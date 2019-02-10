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
        
        public static final byte[] $classHash = new byte[] { 5, 35, -122, -126,
        6, -4, -25, 106, -61, 100, 52, 15, 80, 94, 70, -120, 52, -16, 49, 66,
        83, 5, -101, 93, 95, 12, -50, 69, -77, -60, 14, 94 };
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
        
        public static final byte[] $classHash = new byte[] { 124, 4, 58, 48,
        101, 30, -122, 82, -33, -84, -92, 126, -96, -59, -18, 121, 98, 111, 10,
        38, 55, -67, -25, 51, -128, -5, 35, 59, -115, 115, -34, -7 };
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
                        fabric.worker.transaction.TransactionManager $tm787 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled790 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff788 = 1;
                        boolean $doBackoff789 = true;
                        boolean $retry783 = true;
                        boolean $keepReads784 = false;
                        $label781: for (boolean $commit782 = false; !$commit782;
                                        ) {
                            if ($backoffEnabled790) {
                                if ($doBackoff789) {
                                    if ($backoff788 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff788));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e785) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff788 < 5000) $backoff788 *= 2;
                                }
                                $doBackoff789 = $backoff788 <= 32 ||
                                                  !$doBackoff789;
                            }
                            $commit782 = true;
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
                                         RetryException $e785) {
                                    throw $e785;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e785) {
                                    throw $e785;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e785) {
                                    throw $e785;
                                }
                                catch (final Throwable $e785) {
                                    $tm787.getCurrentLog().checkRetrySignal();
                                    throw $e785;
                                }
                            }
                            catch (final fabric.worker.RetryException $e785) {
                                $commit782 = false;
                                continue $label781;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e785) {
                                $commit782 = false;
                                $retry783 = false;
                                $keepReads784 = $e785.keepReads;
                                if ($tm787.checkForStaleObjects()) {
                                    $retry783 = true;
                                    $keepReads784 = false;
                                    continue $label781;
                                }
                                fabric.common.TransactionID $currentTid786 =
                                  $tm787.getCurrentTid();
                                if ($e785.tid ==
                                      null ||
                                      !$e785.tid.isDescendantOf(
                                                   $currentTid786)) {
                                    throw $e785;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e785);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e785) {
                                $commit782 = false;
                                fabric.common.TransactionID $currentTid786 =
                                  $tm787.getCurrentTid();
                                if ($e785.tid.isDescendantOf($currentTid786))
                                    continue $label781;
                                if ($currentTid786.parent != null) {
                                    $retry783 = false;
                                    throw $e785;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e785) {
                                $commit782 = false;
                                if ($tm787.checkForStaleObjects())
                                    continue $label781;
                                $retry783 = false;
                                throw new fabric.worker.AbortException($e785);
                            }
                            finally {
                                if ($commit782) {
                                    fabric.common.TransactionID $currentTid786 =
                                      $tm787.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e785) {
                                        $commit782 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e785) {
                                        $commit782 = false;
                                        $retry783 = false;
                                        $keepReads784 = $e785.keepReads;
                                        if ($tm787.checkForStaleObjects()) {
                                            $retry783 = true;
                                            $keepReads784 = false;
                                            continue $label781;
                                        }
                                        if ($e785.tid ==
                                              null ||
                                              !$e785.tid.isDescendantOf(
                                                           $currentTid786))
                                            throw $e785;
                                        throw new fabric.worker.
                                                UserAbortException($e785);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e785) {
                                        $commit782 = false;
                                        $currentTid786 = $tm787.getCurrentTid();
                                        if ($currentTid786 != null) {
                                            if ($e785.tid.equals(
                                                            $currentTid786) ||
                                                  !$e785.tid.
                                                  isDescendantOf(
                                                    $currentTid786)) {
                                                throw $e785;
                                            }
                                        }
                                    }
                                } else if ($keepReads784) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit782) {
                                    {  }
                                    if ($retry783) { continue $label781; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 30, -87, 63, -34, 118,
    105, 70, -73, 20, 97, 67, 114, 3, -90, -35, -42, -123, -75, 46, 14, -28,
    -90, 56, -82, -128, 31, 39, 97, 44, 74, 3, 39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
