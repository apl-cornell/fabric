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
        
        public static final byte[] $classHash = new byte[] { 59, -67, -67, -124,
        -94, -85, -66, 103, 79, -29, -92, -63, 66, -28, -36, -80, 111, -54, 117,
        -12, -13, -67, 58, 124, -74, 92, 59, 86, -60, -91, 101, 112 };
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
        
        public static final byte[] $classHash = new byte[] { 86, -49, -28, 25,
        39, -12, 100, -128, 96, -50, -47, -68, -104, 75, 23, -95, -70, 32, 92,
        114, 71, -18, 35, 28, 35, 62, -110, 65, -61, -23, 118, -83 };
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
                        fabric.worker.transaction.TransactionManager $tm809 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled812 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff810 = 1;
                        boolean $doBackoff811 = true;
                        boolean $retry805 = true;
                        boolean $keepReads806 = false;
                        $label803: for (boolean $commit804 = false; !$commit804;
                                        ) {
                            if ($backoffEnabled812) {
                                if ($doBackoff811) {
                                    if ($backoff810 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff810));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e807) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff810 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff810 =
                                          java.lang.Math.
                                            min(
                                              $backoff810 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff811 = $backoff810 <= 32 ||
                                                  !$doBackoff811;
                            }
                            $commit804 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.LinkedList._Static._Proxy.$instance.
                                  set$serialVersionUID((long)
                                                         876323262645176354L);
                            }
                            catch (final fabric.worker.RetryException $e807) {
                                $commit804 = false;
                                continue $label803;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e807) {
                                $commit804 = false;
                                $retry805 = false;
                                $keepReads806 = $e807.keepReads;
                                fabric.common.TransactionID $currentTid808 =
                                  $tm809.getCurrentTid();
                                if ($e807.tid ==
                                      null ||
                                      !$e807.tid.isDescendantOf(
                                                   $currentTid808)) {
                                    throw $e807;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e807);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e807) {
                                $commit804 = false;
                                fabric.common.TransactionID $currentTid808 =
                                  $tm809.getCurrentTid();
                                if ($e807.tid.isDescendantOf($currentTid808))
                                    continue $label803;
                                if ($currentTid808.parent != null) {
                                    $retry805 = false;
                                    throw $e807;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e807) {
                                $commit804 = false;
                                $retry805 = false;
                                if ($tm809.inNestedTxn()) {
                                    $keepReads806 = true;
                                }
                                throw $e807;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid808 =
                                  $tm809.getCurrentTid();
                                if ($commit804) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e807) {
                                        $commit804 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e807) {
                                        $commit804 = false;
                                        $retry805 = false;
                                        $keepReads806 = $e807.keepReads;
                                        if ($e807.tid ==
                                              null ||
                                              !$e807.tid.isDescendantOf(
                                                           $currentTid808))
                                            throw $e807;
                                        throw new fabric.worker.
                                                UserAbortException($e807);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e807) {
                                        $commit804 = false;
                                        $currentTid808 = $tm809.getCurrentTid();
                                        if ($currentTid808 != null) {
                                            if ($e807.tid.equals(
                                                            $currentTid808) ||
                                                  !$e807.tid.
                                                  isDescendantOf(
                                                    $currentTid808)) {
                                                throw $e807;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm809.inNestedTxn() &&
                                          $tm809.checkForStaleObjects()) {
                                        $retry805 = true;
                                        $keepReads806 = false;
                                    }
                                    if ($keepReads806) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e807) {
                                            $currentTid808 = $tm809.getCurrentTid();
                                            if ($currentTid808 != null &&
                                                  ($e807.tid.equals($currentTid808) || !$e807.tid.isDescendantOf($currentTid808))) {
                                                throw $e807;
                                            } else {
                                                $retry805 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit804) {
                                    {  }
                                    if ($retry805) { continue $label803; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -52, -38, 116, 62, 107,
    -44, -31, -7, 78, 66, 47, 0, -55, -52, 66, -31, 112, -114, 9, -88, -71, 81,
    -21, 82, 81, 1, -20, -72, 118, -122, 57, 28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
