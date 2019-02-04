package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
  extends fabric.util.List, fabric.util.AbstractSequentialList {
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
    public static interface Entry extends fabric.lang.Object {
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
          implements Entry {
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
            
            public fabric.util.LinkedList.Entry fabric$util$LinkedList$Entry$(
              fabric.lang.Object arg1) {
                return ((fabric.util.LinkedList.Entry) fetch()).
                  fabric$util$LinkedList$Entry$(arg1);
            }
            
            public _Proxy(Entry._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements Entry {
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
            public Entry fabric$util$LinkedList$Entry$(
              fabric.lang.Object data) {
                fabric$lang$Object$();
                this.set$data(data);
                return (Entry) this.$getProxy();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (Entry) this.$getProxy();
            }
            
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
                this.data = (fabric.lang.Object)
                              $readRef(fabric.lang.Object._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
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
              implements fabric.util.LinkedList.Entry._Static {
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
                      _Impl
                      impl =
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
              implements fabric.util.LinkedList.Entry._Static {
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
                    return new fabric.util.LinkedList.Entry._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -123, -10, -86,
        -36, -73, -33, 14, -98, 91, -86, -68, -98, 61, 37, -96, -53, 16, -121,
        -38, -67, 11, 63, 76, 44, -48, 37, -122, 72, 33, 12, -37, -68 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * Obtain the Entry at a given position in a list. This method of course
   * takes linear time, but it is intelligent enough to take the shorter of the
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
    public fabric.util.LinkedList fabric$util$LinkedList$(fabric.util.Collection c);
    
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
   *        next(), or size() to be initially positioned at the end of the list
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
    public fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);
    
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
    public fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);
    
    /**
   * A ListIterator over the list. This class keeps track of its
   * position in the list and the two list entries it is between.
   *
   * @author Original author unknown
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface LinkedListItr
      extends fabric.util.ListIterator, fabric.lang.Object {
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
     * Adds an element between the previous and next, and advance to the next.
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
          implements LinkedListItr {
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
            
            public fabric.util.LinkedList.LinkedListItr
              fabric$util$LinkedList$LinkedListItr$(int arg1) {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).
                  fabric$util$LinkedList$LinkedListItr$(arg1);
            }
            
            public int nextIndex() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).
                  nextIndex();
            }
            
            public int previousIndex() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).
                  previousIndex();
            }
            
            public boolean hasNext() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).hasNext(
                                                                          );
            }
            
            public boolean hasPrevious() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).
                  hasPrevious();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).next();
            }
            
            public fabric.lang.Object previous() {
                return ((fabric.util.LinkedList.LinkedListItr) fetch()).
                  previous();
            }
            
            public void remove() {
                ((fabric.util.LinkedList.LinkedListItr) fetch()).remove();
            }
            
            public void add(fabric.lang.Object arg1) {
                ((fabric.util.LinkedList.LinkedListItr) fetch()).add(arg1);
            }
            
            public void set(fabric.lang.Object arg1) {
                ((fabric.util.LinkedList.LinkedListItr) fetch()).set(arg1);
            }
            
            public _Proxy(LinkedListItr._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements LinkedListItr {
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
            public LinkedListItr fabric$util$LinkedList$LinkedListItr$(
              int index) {
                fabric$lang$Object$();
                this.set$knownMod((int) this.get$out$().get$modCount());
                if (index == this.get$out$().get$size()) {
                    this.set$next(null);
                    this.set$previous(this.get$out$().get$last());
                } else {
                    this.set$next(this.get$out$().getEntry(index));
                    this.set$previous(this.get$next().get$previous());
                }
                this.set$position((int) index);
                return (LinkedListItr) this.$getProxy();
            }
            
            /**
     * Checks for iterator consistency.
     *
     * @throws ConcurrentModificationException if the list was modified
     */
            private void checkMod() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
            }
            
            /**
     * Returns the index of the next element.
     *
     * @return the next index
     */
            public int nextIndex() { return this.get$position(); }
            
            /**
     * Returns the index of the previous element.
     *
     * @return the previous index
     */
            public int previousIndex() { return this.get$position() - 1; }
            
            /**
     * Returns true if more elements exist via next.
     *
     * @return true if next will succeed
     */
            public boolean hasNext() {
                return !fabric.lang.Object._Proxy.idEquals(this.get$next(),
                                                           null);
            }
            
            /**
     * Returns true if more elements exist via previous.
     *
     * @return true if previous will succeed
     */
            public boolean hasPrevious() {
                return !fabric.lang.Object._Proxy.idEquals(this.get$previous(),
                                                           null);
            }
            
            /**
     * Returns the next element.
     *
     * @return the next element
     * @throws ConcurrentModificationException if the list was modified
     * @throws NoSuchElementException if there is no next
     */
            public fabric.lang.Object next() {
                ((fabric.util.LinkedList.LinkedListItr._Impl) this.fetch()).
                  checkMod();
                if (fabric.lang.Object._Proxy.idEquals(this.get$next(), null))
                    throw new fabric.util.NoSuchElementException();
                this.postInc$position();
                this.set$lastReturned(this.set$previous(this.get$next()));
                this.set$next(this.get$lastReturned().get$next());
                return this.get$lastReturned().get$data();
            }
            
            /**
     * Returns the previous element.
     *
     * @return the previous element
     * @throws ConcurrentModificationException if the list was modified
     * @throws NoSuchElementException if there is no previous
     */
            public fabric.lang.Object previous() {
                ((fabric.util.LinkedList.LinkedListItr._Impl) this.fetch()).
                  checkMod();
                if (fabric.lang.Object._Proxy.idEquals(this.get$previous(),
                                                       null))
                    throw new fabric.util.NoSuchElementException();
                this.postDec$position();
                this.set$lastReturned(this.set$next(this.get$previous()));
                this.set$previous(this.get$lastReturned().get$previous());
                return this.get$lastReturned().get$data();
            }
            
            /**
     * Remove the most recently returned element from the list.
     *
     * @throws ConcurrentModificationException if the list was modified
     * @throws IllegalStateException if there was no last element
     */
            public void remove() {
                ((fabric.util.LinkedList.LinkedListItr._Impl) this.fetch()).
                  checkMod();
                if (fabric.lang.Object._Proxy.idEquals(this.get$lastReturned(),
                                                       null))
                    throw new java.lang.IllegalStateException();
                if (fabric.lang.Object._Proxy.idEquals(this.get$lastReturned(),
                                                       this.get$previous()))
                    this.postDec$position();
                this.set$next(this.get$lastReturned().get$next());
                this.set$previous(this.get$lastReturned().get$previous());
                this.get$out$().removeEntry(this.get$lastReturned());
                this.postInc$knownMod();
                this.set$lastReturned(null);
            }
            
            /**
     * Adds an element between the previous and next, and advance to the next.
     *
     * @param o the element to add
     * @throws ConcurrentModificationException if the list was modified
     */
            public void add(fabric.lang.Object o) {
                ((fabric.util.LinkedList.LinkedListItr._Impl) this.fetch()).
                  checkMod();
                this.get$out$().postInc$modCount();
                this.postInc$knownMod();
                this.get$out$().postInc$size();
                this.postInc$position();
                Entry
                  e =
                  (Entry)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.util.LinkedList.Entry)
                         new fabric.util.LinkedList.Entry._Impl(
                           this.$getStore()).$getProxy()).
                          fabric$util$LinkedList$Entry$(o));
                e.set$previous(this.get$previous());
                e.set$next(this.get$next());
                if (!fabric.lang.Object._Proxy.idEquals(this.get$previous(),
                                                        null))
                    this.get$previous().set$next(e); else
                    this.get$out$().set$first(e);
                if (!fabric.lang.Object._Proxy.idEquals(this.get$next(), null))
                    this.get$next().set$previous(e); else
                    this.get$out$().set$last(e);
                this.set$previous(e);
                this.set$lastReturned(null);
            }
            
            /**
     * Changes the contents of the element most recently returned.
     *
     * @param o the new element
     * @throws ConcurrentModificationException if the list was modified
     * @throws IllegalStateException if there was no last element
     */
            public void set(fabric.lang.Object o) {
                ((fabric.util.LinkedList.LinkedListItr._Impl) this.fetch()).
                  checkMod();
                if (fabric.lang.Object._Proxy.idEquals(this.get$lastReturned(),
                                                       null))
                    throw new java.lang.IllegalStateException();
                this.get$lastReturned().set$data(o);
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (LinkedListItr) this.$getProxy();
            }
            
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
                this.out$ = (fabric.util.LinkedList)
                              $readRef(fabric.util.LinkedList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
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
              implements fabric.util.LinkedList.LinkedListItr._Static {
                public _Proxy(fabric.util.LinkedList.LinkedListItr._Static.
                                _Impl impl) { super(impl); }
                
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
                      _Impl
                      impl =
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
              implements fabric.util.LinkedList.LinkedListItr._Static {
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
                    return new fabric.util.LinkedList.LinkedListItr._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 85, -70, 50, 35,
        14, -47, 116, -42, 34, 77, -60, 26, -116, 39, 32, 14, 82, 23, 116, 54,
        24, -10, 88, 31, -104, 88, -30, 54, 109, 84, 46, -102 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525719795000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.util.AbstractSequentialList._Proxy
      implements fabric.util.LinkedList {
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
        
        public fabric.util.LinkedList.Entry getEntry(int arg1) {
            return ((fabric.util.LinkedList) fetch()).getEntry(arg1);
        }
        
        public void removeEntry(fabric.util.LinkedList.Entry arg1) {
            ((fabric.util.LinkedList) fetch()).removeEntry(arg1);
        }
        
        public fabric.util.LinkedList fabric$util$LinkedList$() {
            return ((fabric.util.LinkedList) fetch()).fabric$util$LinkedList$();
        }
        
        public fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection arg1) {
            return ((fabric.util.LinkedList) fetch()).fabric$util$LinkedList$(
                                                        arg1);
        }
        
        public fabric.lang.Object getFirst() {
            return ((fabric.util.LinkedList) fetch()).getFirst();
        }
        
        public fabric.lang.Object getLast() {
            return ((fabric.util.LinkedList) fetch()).getLast();
        }
        
        public fabric.lang.Object removeFirst() {
            return ((fabric.util.LinkedList) fetch()).removeFirst();
        }
        
        public fabric.lang.Object removeLast() {
            return ((fabric.util.LinkedList) fetch()).removeLast();
        }
        
        public void addFirst(fabric.lang.Object arg1) {
            ((fabric.util.LinkedList) fetch()).addFirst(arg1);
        }
        
        public void addLast(fabric.lang.Object arg1) {
            ((fabric.util.LinkedList) fetch()).addLast(arg1);
        }
        
        public _Proxy(LinkedList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.AbstractSequentialList._Impl
      implements fabric.util.LinkedList {
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
        int size = 0;
        
        /**
   * Obtain the Entry at a given position in a list. This method of course
   * takes linear time, but it is intelligent enough to take the shorter of the
   * paths to get to the Entry required. This implies that the first or last
   * entry in the list is obtained in constant time, which is a very desirable
   * property.
   * For speed and flexibility, range checking is not done in this method:
   * Incorrect values will be returned if (n &lt; 0) or (n &gt;= size).
   *
   * @param n the number of the entry to get
   * @return the entry at position n
   */
        public Entry getEntry(int n) {
            Entry e;
            if (n < this.get$size() / 2) {
                e = this.get$first();
                while (n-- > 0) e = e.get$next();
            } else {
                e = this.get$last();
                while (++n < this.get$size()) e = e.get$previous();
            }
            return e;
        }
        
        /**
   * Remove an entry from the list. This will adjust size and deal with
   *  `first' and  `last' appropriatly.
   *
   * @param e the entry to remove
   */
        public void removeEntry(Entry e) {
            this.postInc$modCount();
            this.postDec$size();
            if (this.get$size() == 0)
                this.set$first(this.set$last(null));
            else {
                if (fabric.lang.Object._Proxy.idEquals(e, this.get$first())) {
                    this.set$first(e.get$next());
                    e.get$next().set$previous(null);
                }
                else if (fabric.lang.Object._Proxy.idEquals(e,
                                                            this.get$last())) {
                    this.set$last(e.get$previous());
                    e.get$previous().set$next(null);
                } else {
                    e.get$next().set$previous(e.get$previous());
                    e.get$previous().set$next(e.get$next());
                }
            }
        }
        
        /**
   * Checks that the index is in the range of possible elements (inclusive).
   *
   * @param index the index to check
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size
   */
        private void checkBoundsInclusive(int index) {
            if (index < 0 || index > this.get$size())
                throw new java.lang.IndexOutOfBoundsException(
                        "Index: " + index + ", Size:" + this.get$size());
        }
        
        /**
   * Checks that the index is in the range of existing elements (exclusive).
   *
   * @param index the index to check
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size
   */
        private void checkBoundsExclusive(int index) {
            if (index < 0 || index >= this.get$size())
                throw new java.lang.IndexOutOfBoundsException(
                        "Index: " + index + ", Size:" + this.get$size());
        }
        
        /**
   * Create an empty linked list.
   */
        public fabric.util.LinkedList fabric$util$LinkedList$() {
            fabric$util$AbstractSequentialList$();
            return (fabric.util.LinkedList) this.$getProxy();
        }
        
        /**
   * Create a linked list containing the elements, in order, of a given
   * collection.
   *
   * @param c the collection to populate this list from
   * @throws NullPointerException if c is null
   */
        public fabric.util.LinkedList fabric$util$LinkedList$(
          fabric.util.Collection c) {
            fabric$util$AbstractSequentialList$();
            addAll(c);
            return (fabric.util.LinkedList) this.$getProxy();
        }
        
        /**
   * Returns the first element in the list.
   *
   * @return the first list element
   * @throws NoSuchElementException if the list is empty
   */
        public fabric.lang.Object getFirst() {
            if (this.get$size() == 0)
                throw new fabric.util.NoSuchElementException();
            return this.get$first().get$data();
        }
        
        /**
   * Returns the last element in the list.
   *
   * @return the last list element
   * @throws NoSuchElementException if the list is empty
   */
        public fabric.lang.Object getLast() {
            if (this.get$size() == 0)
                throw new fabric.util.NoSuchElementException();
            return this.get$last().get$data();
        }
        
        /**
   * Remove and return the first element in the list.
   *
   * @return the former first element in the list
   * @throws NoSuchElementException if the list is empty
   */
        public fabric.lang.Object removeFirst() {
            if (this.get$size() == 0)
                throw new fabric.util.NoSuchElementException();
            this.postInc$modCount();
            this.postDec$size();
            fabric.lang.Object r = this.get$first().get$data();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$first().get$next(),
                                                    null))
                this.get$first().get$next().set$previous(null); else
                this.set$last(null);
            this.set$first(this.get$first().get$next());
            return r;
        }
        
        /**
   * Remove and return the last element in the list.
   *
   * @return the former last element in the list
   * @throws NoSuchElementException if the list is empty
   */
        public fabric.lang.Object removeLast() {
            if (this.get$size() == 0)
                throw new fabric.util.NoSuchElementException();
            this.postInc$modCount();
            this.postDec$size();
            fabric.lang.Object r = this.get$last().get$data();
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$last().get$previous(),
                                             null))
                this.get$last().get$previous().set$next(null); else
                this.set$first(null);
            this.set$last(this.get$last().get$previous());
            return r;
        }
        
        /**
   * Insert an element at the first of the list.
   *
   * @param o the element to insert
   */
        public void addFirst(fabric.lang.Object o) {
            Entry
              e =
              (Entry)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedList.Entry)
                     new fabric.util.LinkedList.Entry._Impl(this.$getStore()).
                     $getProxy()).fabric$util$LinkedList$Entry$(o));
            this.postInc$modCount();
            if (this.get$size() == 0)
                this.set$first(this.set$last(e));
            else {
                e.set$next(this.get$first());
                this.get$first().set$previous(e);
                this.set$first(e);
            }
            this.postInc$size();
        }
        
        /**
   * Insert an element at the last of the list.
   *
   * @param o the element to insert
   */
        public void addLast(fabric.lang.Object o) {
            ((fabric.util.LinkedList._Impl) this.fetch()).
              addLastEntry(
                (Entry)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.LinkedList.Entry)
                       new fabric.util.LinkedList.Entry._Impl(this.$getStore()).
                       $getProxy()).fabric$util$LinkedList$Entry$(o)));
        }
        
        /**
   * Inserts an element at the end of the list.
   *
   * @param e the entry to add
   */
        private void addLastEntry(Entry e) {
            this.postInc$modCount();
            if (this.get$size() == 0)
                this.set$first(this.set$last(e));
            else {
                e.set$previous(this.get$last());
                this.get$last().set$next(e);
                this.set$last(e);
            }
            this.postInc$size();
        }
        
        /**
   * Returns true if the list contains the given object. Comparison is done by
   * <code>o == null ? e = null : o.equals(e)</code>.
   *
   * @param o the element to look for
   * @return true if it is found
   */
        public boolean contains(fabric.lang.Object o) {
            Entry e = this.get$first();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                e.get$data()))
                    return true;
                e = e.get$next();
            }
            return false;
        }
        
        /**
   * Returns the size of the list.
   *
   * @return the list size
   */
        public int size() { return this.get$size(); }
        
        /**
   * Adds an element to the end of the list.
   *
   * @param o the entry to add
   * @return true, as it always succeeds
   */
        public boolean add(fabric.lang.Object o) {
            ((fabric.util.LinkedList._Impl) this.fetch()).
              addLastEntry(
                (Entry)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.LinkedList.Entry)
                       new fabric.util.LinkedList.Entry._Impl(this.$getStore()).
                       $getProxy()).fabric$util$LinkedList$Entry$(o)));
            return true;
        }
        
        /**
   * Removes the entry at the lowest index in the list that matches the given
   * object, comparing by <code>o == null ? e = null : o.equals(e)</code>.
   *
   * @param o the object to remove
   * @return true if an instance of the object was removed
   */
        public boolean remove(fabric.lang.Object o) {
            Entry e = this.get$first();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                e.get$data())) {
                    removeEntry(e);
                    return true;
                }
                e = e.get$next();
            }
            return false;
        }
        
        /**
   * Append the elements of the collection in iteration order to the end of
   * this list. If this list is modified externally (for example, if this
   * list is the collection), behavior is unspecified.
   *
   * @param c the collection to append
   * @return true if the list was modified
   * @throws NullPointerException if c is null
   */
        public boolean addAll(fabric.util.Collection c) {
            return addAll(this.get$size(), c);
        }
        
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
        public boolean addAll(int index, fabric.util.Collection c) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsInclusive(
                                                            index);
            int csize = c.size();
            if (csize == 0) return false;
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            Entry after = null;
            Entry before = null;
            if (index != this.get$size()) {
                after = getEntry(index);
                before = after.get$previous();
            } else
                before = this.get$last();
            Entry
              e =
              (Entry)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedList.Entry)
                     new fabric.util.LinkedList.Entry._Impl(this.$getStore()).
                     $getProxy()).fabric$util$LinkedList$Entry$(itr.next()));
            e.set$previous(before);
            Entry prev = e;
            Entry firstNew = e;
            for (int pos = 1; pos < csize; pos++) {
                e =
                  (Entry)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.util.LinkedList.Entry)
                         new fabric.util.LinkedList.Entry._Impl(
                           this.$getStore()).$getProxy()).
                          fabric$util$LinkedList$Entry$(itr.next()));
                e.set$previous(prev);
                prev.set$next(e);
                prev = e;
            }
            this.postInc$modCount();
            this.set$size((int) (this.get$size() + csize));
            prev.set$next(after);
            if (!fabric.lang.Object._Proxy.idEquals(after, null))
                after.set$previous(e); else this.set$last(e);
            if (!fabric.lang.Object._Proxy.idEquals(before, null))
                before.set$next(firstNew); else this.set$first(firstNew);
            return true;
        }
        
        /**
   * Remove all elements from this list.
   */
        public void clear() {
            if (this.get$size() > 0) {
                this.postInc$modCount();
                this.set$first(null);
                this.set$last(null);
                this.set$size((int) 0);
            }
        }
        
        /**
   * Return the element at index.
   *
   * @param index the place to look
   * @return the element at index
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object get(int index) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsExclusive(
                                                            index);
            return getEntry(index).get$data();
        }
        
        /**
   * Replace the element at the given location in the list.
   *
   * @param index which index to change
   * @param o the new element
   * @return the prior element
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object set(int index, fabric.lang.Object o) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsExclusive(
                                                            index);
            Entry e = getEntry(index);
            fabric.lang.Object old = e.get$data();
            e.set$data(o);
            return old;
        }
        
        /**
   * Inserts an element in the given position in the list.
   *
   * @param index where to insert the element
   * @param o the element to insert
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public void add(int index, fabric.lang.Object o) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsInclusive(
                                                            index);
            Entry
              e =
              (Entry)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedList.Entry)
                     new fabric.util.LinkedList.Entry._Impl(this.$getStore()).
                     $getProxy()).fabric$util$LinkedList$Entry$(o));
            if (index < this.get$size()) {
                this.postInc$modCount();
                Entry after = getEntry(index);
                e.set$next(after);
                e.set$previous(after.get$previous());
                if (fabric.lang.Object._Proxy.idEquals(after.get$previous(),
                                                       null)) this.set$first(e);
                else after.get$previous().set$next(e);
                after.set$previous(e);
                this.postInc$size();
            } else
                ((fabric.util.LinkedList._Impl) this.fetch()).addLastEntry(e);
        }
        
        /**
   * Removes the element at the given position from the list.
   *
   * @param index the location of the element to remove
   * @return the removed element
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public fabric.lang.Object remove(int index) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsExclusive(
                                                            index);
            Entry e = getEntry(index);
            removeEntry(e);
            return e.get$data();
        }
        
        /**
   * Returns the first index where the element is located in the list, or -1.
   *
   * @param o the element to look for
   * @return its position, or -1 if not found
   */
        public int indexOf(fabric.lang.Object o) {
            int index = 0;
            Entry e = this.get$first();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                e.get$data()))
                    return index;
                index++;
                e = e.get$next();
            }
            return -1;
        }
        
        /**
   * Returns the last index where the element is located in the list, or -1.
   *
   * @param o the element to look for
   * @return its position, or -1 if not found
   */
        public int lastIndexOf(fabric.lang.Object o) {
            int index = this.get$size() - 1;
            Entry e = this.get$last();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                e.get$data()))
                    return index;
                index--;
                e = e.get$previous();
            }
            return -1;
        }
        
        /**
   * Obtain a ListIterator over this list, starting at a given index. The
   * ListIterator returned by this method supports the add, remove and set
   * methods.
   *
   * @param index the index of the element to be returned by the first call to
   *        next(), or size() to be initially positioned at the end of the list
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                     int index) {
            ((fabric.util.LinkedList._Impl) this.fetch()).checkBoundsInclusive(
                                                            index);
            return (LinkedListItr)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.LinkedList.LinkedListItr)
                          new fabric.util.LinkedList.LinkedListItr._Impl(
                            store, (fabric.util.LinkedList) this.$getProxy()).
                          $getProxy()).fabric$util$LinkedList$LinkedListItr$(
                                         index));
        }
        
        /**
   * Returns an array which contains the elements of the list in order.
   *
   * @return an array containing the list elements
   */
        public fabric.lang.arrays.ObjectArray toArray() {
            fabric.lang.arrays.ObjectArray array =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.lang.Object._Proxy.class,
                                      this.get$size()).$getProxy();
            Entry e = this.get$first();
            for (int i = 0; i < this.get$size(); i++) {
                array.set(i, e.get$data());
                e = e.get$next();
            }
            return array;
        }
        
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
          fabric.lang.arrays.ObjectArray a) {
            if (a.get$length() < this.get$size())
                a =
                  (fabric.lang.arrays.ObjectArray)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.
                          $wrap(
                            java.lang.reflect.Array.
                                newInstance(
                                  fabric.lang.Object._Proxy.
                                      $getProxy(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(a)).getClass().
                                      getComponentType(),
                                  this.get$size())));
            else if (a.get$length() > this.get$size())
                a.set(this.get$size(), null);
            Entry e = this.get$first();
            for (int i = 0; i < this.get$size(); i++) {
                a.set(i, e.get$data());
                e = e.get$next();
            }
            return a;
        }
        
        /**
   * Serializes this object to the given stream.
   *
   * @param s the stream to write to
   * @throws IOException if the underlying stream fails
   * @serialData the size of the list (int), followed by all the elements
   *             (Object) in proper order
   */
        private void writeObject(java.io.ObjectOutputStream s)
              throws java.io.IOException {
            s.defaultWriteObject();
            s.writeInt(this.get$size());
            Entry e = this.get$first();
            while (!fabric.lang.Object._Proxy.idEquals(e, null)) {
                s.writeObject(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(e.get$data()));
                e = e.get$next();
            }
        }
        
        /**
   * Deserializes this object from the given stream.
   *
   * @param s the stream to read from
   * @throws ClassNotFoundException if the underlying stream fails
   * @throws IOException if the underlying stream fails
   * @serialData the size of the list (int), followed by all the elements
   *             (Object) in proper order
   */
        private void readObject(java.io.ObjectInputStream s)
              throws java.io.IOException, java.lang.ClassNotFoundException {
            s.defaultReadObject();
            int i = s.readInt();
            while (--i >= 0)
                ((fabric.util.LinkedList._Impl) this.fetch()).
                  addLastEntry(
                    (Entry)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.util.LinkedList.Entry)
                           new fabric.util.LinkedList.Entry._Impl(
                             this.$getStore()).$getProxy()).
                            fabric$util$LinkedList$Entry$(
                              (fabric.lang.Object)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(s.readObject())))));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.LinkedList) this.$getProxy();
        }
        
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
            fabric.util.LinkedList._Impl src = (fabric.util.LinkedList._Impl)
                                                 other;
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
          implements fabric.util.LinkedList._Static {
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
                  _Impl
                  impl =
                  (fabric.util.LinkedList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.LinkedList._Static._Impl.class);
                $instance = (fabric.util.LinkedList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedList._Static {
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
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.LinkedList._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm550 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled553 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff551 = 1;
                        boolean $doBackoff552 = true;
                        boolean $retry546 = true;
                        boolean $keepReads547 = false;
                        $label544: for (boolean $commit545 = false; !$commit545;
                                        ) {
                            if ($backoffEnabled553) {
                                if ($doBackoff552) {
                                    if ($backoff551 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff551));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e548) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff551 < 5000) $backoff551 *= 2;
                                }
                                $doBackoff552 = $backoff551 <= 32 ||
                                                  !$doBackoff552;
                            }
                            $commit545 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.LinkedList._Static._Proxy.
                                      $instance.
                                      set$serialVersionUID(
                                        (long) 876323262645176354L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e548) {
                                    throw $e548;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e548) {
                                    throw $e548;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e548) {
                                    throw $e548;
                                }
                                catch (final Throwable $e548) {
                                    $tm550.getCurrentLog().checkRetrySignal();
                                    throw $e548;
                                }
                            }
                            catch (final fabric.worker.RetryException $e548) {
                                $commit545 = false;
                                continue $label544;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e548) {
                                $commit545 = false;
                                $retry546 = false;
                                $keepReads547 = $e548.keepReads;
                                if ($tm550.checkForStaleObjects()) {
                                    $retry546 = true;
                                    $keepReads547 = false;
                                    continue $label544;
                                }
                                fabric.common.TransactionID $currentTid549 =
                                  $tm550.getCurrentTid();
                                if ($e548.tid ==
                                      null ||
                                      !$e548.tid.isDescendantOf(
                                                   $currentTid549)) {
                                    throw $e548;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e548);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e548) {
                                $commit545 = false;
                                fabric.common.TransactionID $currentTid549 =
                                  $tm550.getCurrentTid();
                                if ($e548.tid.isDescendantOf($currentTid549))
                                    continue $label544;
                                if ($currentTid549.parent != null) {
                                    $retry546 = false;
                                    throw $e548;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e548) {
                                $commit545 = false;
                                if ($tm550.checkForStaleObjects())
                                    continue $label544;
                                $retry546 = false;
                                throw new fabric.worker.AbortException($e548);
                            }
                            finally {
                                if ($commit545) {
                                    fabric.common.TransactionID $currentTid549 =
                                      $tm550.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e548) {
                                        $commit545 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e548) {
                                        $commit545 = false;
                                        $retry546 = false;
                                        $keepReads547 = $e548.keepReads;
                                        if ($tm550.checkForStaleObjects()) {
                                            $retry546 = true;
                                            $keepReads547 = false;
                                            continue $label544;
                                        }
                                        if ($e548.tid ==
                                              null ||
                                              !$e548.tid.isDescendantOf(
                                                           $currentTid549))
                                            throw $e548;
                                        throw new fabric.worker.
                                                UserAbortException($e548);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e548) {
                                        $commit545 = false;
                                        $currentTid549 = $tm550.getCurrentTid();
                                        if ($currentTid549 != null) {
                                            if ($e548.tid.equals(
                                                            $currentTid549) ||
                                                  !$e548.tid.
                                                  isDescendantOf(
                                                    $currentTid549)) {
                                                throw $e548;
                                            }
                                        }
                                    }
                                } else if ($keepReads547) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit545) {
                                    {  }
                                    if ($retry546) { continue $label544; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 52, 34, -31, -44, 7,
    -92, 123, -83, 121, 11, 15, 32, -44, -38, 124, 20, 95, 82, -83, 119, -44,
    93, 8, 88, -30, 56, -38, -64, 5, 112, -64, 7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
