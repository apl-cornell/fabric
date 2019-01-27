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
        
        public static final byte[] $classHash = new byte[] { 29, 2, 22, 110,
        -96, -101, 5, -111, -36, -71, 96, 110, 95, 106, 77, -30, -97, 127, 30,
        84, 68, -68, -111, 83, -102, -15, 49, 33, 94, 101, 26, 7 };
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
        
        public static final byte[] $classHash = new byte[] { -71, -127, 55, 127,
        -14, 64, 68, 83, 102, -84, 50, -67, -64, 82, -16, 16, -108, -117, -124,
        88, 116, 29, -42, 57, -36, -44, 109, -111, 17, -55, -62, 99 };
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
                        fabric.worker.transaction.TransactionManager $tm821 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled824 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff822 = 1;
                        boolean $doBackoff823 = true;
                        boolean $retry817 = true;
                        boolean $keepReads818 = false;
                        $label815: for (boolean $commit816 = false; !$commit816;
                                        ) {
                            if ($backoffEnabled824) {
                                if ($doBackoff823) {
                                    if ($backoff822 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff822));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e819) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff822 < 5000) $backoff822 *= 2;
                                }
                                $doBackoff823 = $backoff822 <= 32 ||
                                                  !$doBackoff823;
                            }
                            $commit816 = true;
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
                                         RetryException $e819) {
                                    throw $e819;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e819) {
                                    throw $e819;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e819) {
                                    throw $e819;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e819) {
                                    throw $e819;
                                }
                                catch (final Throwable $e819) {
                                    $tm821.getCurrentLog().checkRetrySignal();
                                    throw $e819;
                                }
                            }
                            catch (final fabric.worker.RetryException $e819) {
                                $commit816 = false;
                                continue $label815;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e819) {
                                $commit816 = false;
                                $retry817 = false;
                                $keepReads818 = $e819.keepReads;
                                if ($tm821.checkForStaleObjects()) {
                                    $retry817 = true;
                                    $keepReads818 = false;
                                    continue $label815;
                                }
                                fabric.common.TransactionID $currentTid820 =
                                  $tm821.getCurrentTid();
                                if ($e819.tid ==
                                      null ||
                                      !$e819.tid.isDescendantOf(
                                                   $currentTid820)) {
                                    throw $e819;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e819);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e819) {
                                $commit816 = false;
                                fabric.common.TransactionID $currentTid820 =
                                  $tm821.getCurrentTid();
                                if ($e819.tid.isDescendantOf($currentTid820))
                                    continue $label815;
                                if ($currentTid820.parent != null) {
                                    $retry817 = false;
                                    throw $e819;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e819) {
                                $commit816 = false;
                                if ($tm821.checkForStaleObjects())
                                    continue $label815;
                                fabric.common.TransactionID $currentTid820 =
                                  $tm821.getCurrentTid();
                                if ($e819.tid.isDescendantOf($currentTid820)) {
                                    $retry817 = true;
                                }
                                else if ($currentTid820.parent != null) {
                                    $retry817 = false;
                                    throw $e819;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e819) {
                                $commit816 = false;
                                if ($tm821.checkForStaleObjects())
                                    continue $label815;
                                $retry817 = false;
                                throw new fabric.worker.AbortException($e819);
                            }
                            finally {
                                if ($commit816) {
                                    fabric.common.TransactionID $currentTid820 =
                                      $tm821.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e819) {
                                        $commit816 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e819) {
                                        $commit816 = false;
                                        $retry817 = false;
                                        $keepReads818 = $e819.keepReads;
                                        if ($tm821.checkForStaleObjects()) {
                                            $retry817 = true;
                                            $keepReads818 = false;
                                            continue $label815;
                                        }
                                        if ($e819.tid ==
                                              null ||
                                              !$e819.tid.isDescendantOf(
                                                           $currentTid820))
                                            throw $e819;
                                        throw new fabric.worker.
                                                UserAbortException($e819);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e819) {
                                        $commit816 = false;
                                        $currentTid820 = $tm821.getCurrentTid();
                                        if ($currentTid820 != null) {
                                            if ($e819.tid.equals(
                                                            $currentTid820) ||
                                                  !$e819.tid.
                                                  isDescendantOf(
                                                    $currentTid820)) {
                                                throw $e819;
                                            }
                                        }
                                    }
                                } else if ($keepReads818) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit816) {
                                    {  }
                                    if ($retry817) { continue $label815; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 49, -54, -66, -56,
    -110, -57, -128, -24, 70, 116, -18, -31, -63, 64, -5, -112, 32, 0, -70, -50,
    -17, 10, 112, 5, -59, -22, 83, 89, 43, -83, 34, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
