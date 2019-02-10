package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * A basic implementation of most of the methods in the List interface to make
 * it easier to create a List based on a random-access data structure. If
 * the list is sequential (such as a linked list), use AbstractSequentialList.
 * To create an unmodifiable list, it is only necessary to override the
 * size() and get(int) methods (this contrasts with all other abstract
 * collection classes which require an iterator to be provided). To make the
 * list modifiable, the set(int, Object) method should also be overridden, and
 * to make the list resizable, the add(int, Object) and remove(int) methods
 * should be overridden too. Other methods should be overridden if the
 * backing data structure allows for a more efficient implementation.
 * The precise implementation used by AbstractList is documented, so that
 * subclasses can tell which methods could be implemented more efficiently.
 * <p>
 *
 * As recommended by Collection and List, the subclass should provide at
 * least a no-argument and a Collection constructor. This class is not
 * synchronized.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see AbstractSequentialList
 * @see AbstractCollection
 * @see ListIterator
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractList
  extends fabric.util.List, fabric.util.AbstractCollection {
    public int get$modCount();
    
    public int set$modCount(int val);
    
    public int postInc$modCount();
    
    public int postDec$modCount();
    
    /**
   * The main constructor, for use by subclasses.
   */
    public fabric.util.AbstractList fabric$util$AbstractList$();
    
    /**
   * Returns the elements at the specified position in the list.
   *
   * @param index the element to return
   * @return the element at that position
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
    public abstract fabric.lang.Object get(int index);
    
    /**
   * Insert an element into the list at a given position (optional operation).
   * This shifts all existing elements from that position to the end one
   * index to the right.  This version of add has no return, since it is
   * assumed to always succeed if there is no exception. This implementation
   * always throws UnsupportedOperationException, and must be overridden to
   * make a modifiable List.  If you want fail-fast iterators, be sure to
   * increment modCount when overriding this.
   *
   * @param index the location to insert the item
   * @param o the object to insert
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason
   * @see #modCount
   */
    public void add(int index, fabric.lang.Object o);
    
    /**
   * Add an element to the end of the list (optional operation). If the list
   * imposes restraints on what can be inserted, such as no null elements,
   * this should be documented. This implementation calls
   * <code>add(size(), o);</code>, and will fail if that version does.
   *
   * @param o the object to add
   * @return true, as defined by Collection for a modified list
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason
   * @see #add(int, Object)
   */
    public boolean add(fabric.lang.Object o);
    
    /**
   * Insert the contents of a collection into the list at a given position
   * (optional operation). Shift all elements at that position to the right
   * by the number of elements inserted. This operation is undefined if
   * this list is modified during the operation (for example, if you try
   * to insert a list into itself). This implementation uses the iterator of
   * the collection, repeatedly calling add(int, Object); this will fail
   * if add does. This can often be made more efficient.
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
   * @see #add(int, Object)
   */
    public boolean addAll(int index, fabric.util.Collection c);
    
    /**
   * Clear the list, such that a subsequent call to isEmpty() would return
   * true (optional operation). This implementation calls
   * <code>removeRange(0, size())</code>, so it will fail unless remove
   * or removeRange is overridden.
   *
   * @throws UnsupportedOperationException if this list does not support the
   *         clear operation
   * @see #remove(int)
   * @see #removeRange(int, int)
   */
    public void clear();
    
    /**
   * Test whether this list is equal to another object. A List is defined to be
   * equal to an object if and only if that object is also a List, and the two
   * lists have the same sequence. Two lists l1 and l2 are equal if and only
   * if <code>l1.size() == l2.size()</code>, and for every integer n between 0
   * and <code>l1.size() - 1</code> inclusive, <code>l1.get(n) == null ?
   * l2.get(n) == null : l1.get(n).equals(l2.get(n))</code>.
   * <p>
   *
   * This implementation returns true if the object is this, or false if the
   * object is not a List.  Otherwise, it iterates over both lists (with
   * iterator(Store)), returning false if two elements compare false or one list
   * is shorter, and true if the iteration completes successfully.
   *
   * @param o the object to test for equality with this list
   * @return true if o is equal to this list
   * @see Object#equals(Object)
   * @see #hashCode()
   */
    public boolean equals(fabric.lang.Object o);
    
    /**
   * Obtains a hash code for this list. In order to obey the general
   * contract of the hashCode method of class Object, this value is
   * calculated as follows:
   *
<pre>hashCode = 1;
Iterator i = list.iterator(LOCAL_STORE);
while (i.hasNext())
{
  Object obj = i.next();
  hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
}</pre>
   *
   * This ensures that the general contract of Object.hashCode() is adhered to.
   *
   * @return the hash code of this list
   *
   * @see Object#hashCode()
   * @see #equals(Object)
   */
    public int hashCode();
    
    /**
   * Obtain the first index at which a given object is to be found in this
   * list. This implementation follows a listIterator(store) until a match is found,
   * or returns -1 if the list end is reached.
   *
   * @param o the object to search for
   * @return the least integer n such that <code>o == null ? get(n) == null :
   *         o.equals(get(n))</code>, or -1 if there is no such index
   */
    public int indexOf(fabric.lang.Object o);
    
    /**
   * Obtain an Iterator over this list, whose sequence is the list order.
   * This implementation uses size(), get(int), and remove(int) of the
   * backing list, and does not support remove unless the list does. This
   * implementation is fail-fast if you correctly maintain modCount.
   * Also, this implementation is specified by Sun to be distinct from
   * listIterator, although you could easily implement it as
   * <code>return listIterator(0)</code>.
   *
   * @return an Iterator over the elements of this list, in order
   * @see #modCount
   */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    public static interface AnonymousIterator
      extends fabric.util.Iterator, fabric.lang.Object {
        public fabric.util.AbstractList get$out$();
        
        public int get$initSize();
        
        public int set$initSize(int val);
        
        public int postInc$initSize();
        
        public int postDec$initSize();
        
        public int get$initModCount();
        
        public int set$initModCount(int val);
        
        public int postInc$initModCount();
        
        public int postDec$initModCount();
        
        public int get$pos();
        
        public int set$pos(int val);
        
        public int postInc$pos();
        
        public int postDec$pos();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        public int get$last();
        
        public int set$last(int val);
        
        public int postInc$last();
        
        public int postDec$last();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public AnonymousIterator fabric$util$AbstractList$AnonymousIterator$();
        
        /**
     * Tests to see if there are any more objects to return.
     *
     * @return True if the end of the list has not yet been reached.
     */
        public boolean hasNext();
        
        /**
     * Retrieves the next object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException
     *           if there are no more objects to retrieve.
     * @throws ConcurrentModificationException
     *           if the list has been modified elsewhere.
     */
        public fabric.lang.Object next();
        
        /**
     * Removes the last object retrieved by <code>next()</code> from the list,
     * if the list supports object removal.
     *
     * @throws ConcurrentModificationException
     *           if the list has been modified elsewhere.
     * @throws IllegalStateException
     *           if the iterator is positioned before the start of the list or
     *           the last object has already been removed.
     * @throws UnsupportedOperationException
     *           if the list does not support removing elements.
     */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements AnonymousIterator {
            public fabric.util.AbstractList get$out$() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$out$();
            }
            
            public int get$initSize() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$initSize();
            }
            
            public int set$initSize(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$initSize(val);
            }
            
            public int postInc$initSize() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$initSize();
            }
            
            public int postDec$initSize() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$initSize();
            }
            
            public int get$initModCount() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$initModCount();
            }
            
            public int set$initModCount(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$initModCount(val);
            }
            
            public int postInc$initModCount() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$initModCount();
            }
            
            public int postDec$initModCount() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$initModCount();
            }
            
            public int get$pos() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$pos();
            }
            
            public int set$pos(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$pos(val);
            }
            
            public int postInc$pos() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$pos();
            }
            
            public int postDec$pos() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$pos();
            }
            
            public int get$size() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$size();
            }
            
            public int set$size(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$size(val);
            }
            
            public int postInc$size() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$size();
            }
            
            public int postDec$size() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$size();
            }
            
            public int get$last() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$last();
            }
            
            public int set$last(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$last(val);
            }
            
            public int postInc$last() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$last();
            }
            
            public int postDec$last() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$last();
            }
            
            public int get$knownMod() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.AbstractList.AnonymousIterator._Impl)
                          fetch()).postDec$knownMod();
            }
            
            public fabric.util.AbstractList.AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$() {
                return ((fabric.util.AbstractList.AnonymousIterator) fetch()).
                  fabric$util$AbstractList$AnonymousIterator$();
            }
            
            public boolean hasNext() {
                return ((fabric.util.AbstractList.AnonymousIterator) fetch()).
                  hasNext();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.AbstractList.AnonymousIterator) fetch()).
                  next();
            }
            
            public void remove() {
                ((fabric.util.AbstractList.AnonymousIterator) fetch()).remove();
            }
            
            public _Proxy(AnonymousIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements AnonymousIterator {
            public fabric.util.AbstractList get$out$() { return this.out$; }
            
            private fabric.util.AbstractList out$;
            
            public int get$initSize() { return this.initSize; }
            
            public int set$initSize(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.initSize = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$initSize() {
                int tmp = this.get$initSize();
                this.set$initSize((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$initSize() {
                int tmp = this.get$initSize();
                this.set$initSize((int) (tmp - 1));
                return tmp;
            }
            
            int initSize;
            
            public int get$initModCount() { return this.initModCount; }
            
            public int set$initModCount(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.initModCount = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$initModCount() {
                int tmp = this.get$initModCount();
                this.set$initModCount((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$initModCount() {
                int tmp = this.get$initModCount();
                this.set$initModCount((int) (tmp - 1));
                return tmp;
            }
            
            int initModCount;
            
            public int get$pos() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.pos;
            }
            
            public int set$pos(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.pos = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$pos() {
                int tmp = this.get$pos();
                this.set$pos((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$pos() {
                int tmp = this.get$pos();
                this.set$pos((int) (tmp - 1));
                return tmp;
            }
            
            private int pos;
            
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
            
            private int size;
            
            public int get$last() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.last;
            }
            
            public int set$last(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.last = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$last() {
                int tmp = this.get$last();
                this.set$last((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$last() {
                int tmp = this.get$last();
                this.set$last((int) (tmp - 1));
                return tmp;
            }
            
            private int last;
            
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
            
            private int knownMod;
            
            public AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$() {
                this.set$initSize((int) this.get$out$().size());
                this.set$initModCount((int) this.get$out$().get$modCount());
                fabric$lang$Object$();
                this.set$pos((int) 0);
                this.set$size((int) this.get$initSize());
                this.set$last((int) -1);
                this.set$knownMod((int) this.get$initModCount());
                return (AnonymousIterator) this.$getProxy();
            }
            
            /**
     * Checks for modifications made to the list from elsewhere while iteration
     * is in progress.
     *
     * @throws ConcurrentModificationException
     *           if the list has been modified elsewhere.
     */
            private void checkMod() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
            }
            
            /**
     * Tests to see if there are any more objects to return.
     *
     * @return True if the end of the list has not yet been reached.
     */
            public boolean hasNext() { return this.get$pos() < this.get$size(); }
            
            /**
     * Retrieves the next object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException
     *           if there are no more objects to retrieve.
     * @throws ConcurrentModificationException
     *           if the list has been modified elsewhere.
     */
            public fabric.lang.Object next() {
                ((fabric.util.AbstractList.AnonymousIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$pos() == this.get$size())
                    throw new fabric.util.NoSuchElementException();
                this.set$last((int) this.get$pos());
                return this.get$out$().get(this.postInc$pos());
            }
            
            /**
     * Removes the last object retrieved by <code>next()</code> from the list,
     * if the list supports object removal.
     *
     * @throws ConcurrentModificationException
     *           if the list has been modified elsewhere.
     * @throws IllegalStateException
     *           if the iterator is positioned before the start of the list or
     *           the last object has already been removed.
     * @throws UnsupportedOperationException
     *           if the list does not support removing elements.
     */
            public void remove() {
                ((fabric.util.AbstractList.AnonymousIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$last() < 0)
                    throw new java.lang.IllegalStateException();
                this.get$out$().remove(this.get$last());
                this.postDec$pos();
                this.postDec$size();
                this.set$last((int) -1);
                this.set$knownMod((int) this.get$out$().get$modCount());
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (AnonymousIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.AbstractList out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList.AnonymousIterator._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                out.writeInt(this.initSize);
                out.writeInt(this.initModCount);
                out.writeInt(this.pos);
                out.writeInt(this.size);
                out.writeInt(this.last);
                out.writeInt(this.knownMod);
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
                this.out$ = (fabric.util.AbstractList)
                              $readRef(fabric.util.AbstractList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.initSize = in.readInt();
                this.initModCount = in.readInt();
                this.pos = in.readInt();
                this.size = in.readInt();
                this.last = in.readInt();
                this.knownMod = in.readInt();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractList.AnonymousIterator._Impl src =
                  (fabric.util.AbstractList.AnonymousIterator._Impl) other;
                this.out$ = src.out$;
                this.initSize = src.initSize;
                this.initModCount = src.initModCount;
                this.pos = src.pos;
                this.size = src.size;
                this.last = src.last;
                this.knownMod = src.knownMod;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.AnonymousIterator._Static {
                public _Proxy(fabric.util.AbstractList.AnonymousIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.AnonymousIterator.
                  _Static $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      AnonymousIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractList.
                        AnonymousIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractList.AnonymousIterator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.AbstractList.AnonymousIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.AnonymousIterator._Static {
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
                    return new fabric.util.AbstractList.AnonymousIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -41, 37, -76, -101,
        33, 119, 7, -104, 125, 115, -4, 76, 63, -65, -119, -1, -61, -94, 66,
        -56, -20, 101, 0, -19, 25, 116, 61, -99, -80, 70, 44, 52 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * Obtain the last index at which a given object is to be found in this
   * list. This implementation grabs listIterator(size()), then searches
   * backwards for a match or returns -1.
   *
   * @return the greatest integer n such that <code>o == null ? get(n) == null
   *         : o.equals(get(n))</code>, or -1 if there is no such index
   */
    public int lastIndexOf(fabric.lang.Object o);
    
    /**
   * Obtain a ListIterator over this list, starting at the beginning. This
   * implementation returns listIterator(0).
   *
   * @return a ListIterator over the elements of this list, in order, starting
   *         at the beginning
   */
    public fabric.util.ListIterator listIterator(fabric.worker.Store store);
    
    /**
   * Obtain a ListIterator over this list, starting at a given position.
   * A first call to next() would return the same as get(index), and a
   * first call to previous() would return the same as get(index - 1).
   * <p>
   *
   * This implementation uses size(), get(int), set(int, Object),
   * add(int, Object), and remove(int) of the backing list, and does not
   * support remove, set, or add unless the list does. This implementation
   * is fail-fast if you correctly maintain modCount.
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from
   * @return a ListIterator over the elements of this list, in order, starting
   *         at index
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @see #modCount
   */
    public fabric.util.ListIterator listIterator(fabric.worker.Store store, final int index);
    
    /**
   * Remove the element at a given position in this list (optional operation).
   * Shifts all remaining elements to the left to fill the gap. This
   * implementation always throws an UnsupportedOperationException.
   * If you want fail-fast iterators, be sure to increment modCount when
   * overriding this.
   *
   * @param index the position within the list of the object to remove
   * @return the object that was removed
   * @throws UnsupportedOperationException if this list does not support the
   *         remove operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   * @see #modCount
   */
    public fabric.lang.Object remove(int index);
    
    /**
   * Remove a subsection of the list. This is called by the clear and
   * removeRange methods of the class which implements subList, which are
   * difficult for subclasses to override directly. Therefore, this method
   * should be overridden instead by the more efficient implementation, if one
   * exists. Overriding this can reduce quadratic efforts to constant time
   * in some cases!
   * <p>
   *
   * This implementation first checks for illegal or out of range arguments. It
   * then obtains a ListIterator over the list using listIterator(fromIndex).
   * It then calls next() and remove() on this iterator repeatedly, toIndex -
   * fromIndex times.
   *
   * @param fromIndex the index, inclusive, to remove from.
   * @param toIndex the index, exclusive, to remove to.
   * @throws UnsupportedOperationException if the list does
   *         not support removing elements.
   */
    public void removeRange(int fromIndex, int toIndex);
    
    /**
   * Replace an element of this list with another object (optional operation).
   * This implementation always throws an UnsupportedOperationException.
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
   */
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    /**
   * Obtain a List view of a subsection of this list, from fromIndex
   * (inclusive) to toIndex (exclusive). If the two indices are equal, the
   * sublist is empty. The returned list should be modifiable if and only
   * if this list is modifiable. Changes to the returned list should be
   * reflected in this list. If this list is structurally modified in
   * any way other than through the returned list, the result of any subsequent
   * operations on the returned list is undefined.
   * <p>
   *
   * This implementation returns a subclass of AbstractList. It stores, in
   * private fields, the offset and size of the sublist, and the expected
   * modCount of the backing list. If the backing list implements RandomAccess,
   * the sublist will also.
   * <p>
   *
   * The subclass's <code>set(int, Object)</code>, <code>get(int)</code>,
   * <code>add(int, Object)</code>, <code>remove(int)</code>,
   * <code>addAll(int, Collection)</code> and
   * <code>removeRange(int, int)</code> methods all delegate to the
   * corresponding methods on the backing abstract list, after
   * bounds-checking the index and adjusting for the offset. The
   * <code>addAll(Collection c)</code> method merely returns addAll(size, c).
   * The <code>listIterator(Store, int)</code> method returns a "wrapper object"
   * over a list iterator on the backing list, which is created with the
   * corresponding method on the backing list. The <code>iterator(Store)</code>
   * method merely returns listIterator(store), and the <code>size()</code> method
   * merely returns the subclass's size field.
   * <p>
   *
   * All methods first check to see if the actual modCount of the backing
   * list is equal to its expected value, and throw a
   * ConcurrentModificationException if it is not.
   *
   * @param fromIndex the index that the returned list should start from
   *        (inclusive)
   * @param toIndex the index that the returned list should go to (exclusive)
   * @return a List backed by a subsection of this list
   * @throws IndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; size()
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @see ConcurrentModificationException
   * @see RandomAccess
   */
    public fabric.util.List subList(int fromIndex, int toIndex);
    
    /**
   *
   */
    public static interface AbstractListIterator
      extends fabric.util.ListIterator, fabric.lang.Object {
        public fabric.util.AbstractList get$out$();
        
        public int get$index();
        
        public int set$index(int val);
        
        public int postInc$index();
        
        public int postDec$index();
        
        public int get$knownMod();
        
        public int set$knownMod(int val);
        
        public int postInc$knownMod();
        
        public int postDec$knownMod();
        
        public int get$position();
        
        public int set$position(int val);
        
        public int postInc$position();
        
        public int postDec$position();
        
        public int get$lastReturned();
        
        public int set$lastReturned(int val);
        
        public int postInc$lastReturned();
        
        public int postDec$lastReturned();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        /**
     * Tests to see if there are any more objects to
     * return.
     *
     * @return True if the end of the list has not yet been
     *         reached.
     */
        public boolean hasNext();
        
        /**
     * Tests to see if there are objects prior to the
     * current position in the list.
     *
     * @return True if objects exist prior to the current
     *         position of the iterator.
     */
        public boolean hasPrevious();
        
        /**
     * Retrieves the next object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException if there are no
     *         more objects to retrieve.
     * @throws ConcurrentModificationException if the
     *         list has been modified elsewhere.
     */
        public fabric.lang.Object next();
        
        /**
     * Retrieves the previous object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException if there are no
     *         previous objects to retrieve.
     * @throws ConcurrentModificationException if the
     *         list has been modified elsewhere.
     */
        public fabric.lang.Object previous();
        
        /**
     * Returns the index of the next element in the
     * list, which will be retrieved by <code>next()</code>
     *
     * @return The index of the next element.
     */
        public int nextIndex();
        
        /**
     * Returns the index of the previous element in the
     * list, which will be retrieved by <code>previous()</code>
     *
     * @return The index of the previous element.
     */
        public int previousIndex();
        
        /**
    * Removes the last object retrieved by <code>next()</code>
    * or <code>previous()</code> from the list, if the list
    * supports object removal.
    *
    * @throws IllegalStateException if the iterator is positioned
    *         before the start of the list or the last object has already
    *         been removed.
    * @throws UnsupportedOperationException if the list does
    *         not support removing elements.
    * @throws ConcurrentModificationException if the list
    *         has been modified elsewhere.
    */
        public void remove();
        
        /**
    * Replaces the last object retrieved by <code>next()</code>
    * or <code>previous</code> with o, if the list supports object
    * replacement and an add or remove operation has not already
    * been performed.
    *
    * @throws IllegalStateException if the iterator is positioned
    *         before the start of the list or the last object has already
    *         been removed.
    * @throws UnsupportedOperationException if the list doesn't support
    *         the addition or removal of elements.
    * @throws ClassCastException if the type of o is not a valid type
    *         for this list.
    * @throws IllegalArgumentException if something else related to o
    *         prevents its addition.
    * @throws ConcurrentModificationException if the list
    *         has been modified elsewhere.
    */
        public void set(fabric.lang.Object o);
        
        /**
     * Adds the supplied object before the element that would be returned
     * by a call to <code>next()</code>, if the list supports addition.
     *
     * @param o The object to add to the list.
     * @throws UnsupportedOperationException if the list doesn't support
     *         the addition of new elements.
     * @throws ClassCastException if the type of o is not a valid type
     *         for this list.
     * @throws IllegalArgumentException if something else related to o
     *         prevents its addition.
     * @throws ConcurrentModificationException if the list
     *         has been modified elsewhere.
     */
        public void add(fabric.lang.Object o);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements AbstractListIterator {
            public fabric.util.AbstractList get$out$() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$out$();
            }
            
            public int get$index() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$index();
            }
            
            public int set$index(int val) {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).set$index(val);
            }
            
            public int postInc$index() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postInc$index();
            }
            
            public int postDec$index() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postDec$index();
            }
            
            public int get$knownMod() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$knownMod();
            }
            
            public int set$knownMod(int val) {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).set$knownMod(val);
            }
            
            public int postInc$knownMod() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postInc$knownMod();
            }
            
            public int postDec$knownMod() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postDec$knownMod();
            }
            
            public int get$position() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$position();
            }
            
            public int set$position(int val) {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).set$position(val);
            }
            
            public int postInc$position() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postInc$position();
            }
            
            public int postDec$position() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postDec$position();
            }
            
            public int get$lastReturned() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$lastReturned();
            }
            
            public int set$lastReturned(int val) {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).set$lastReturned(val);
            }
            
            public int postInc$lastReturned() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postInc$lastReturned();
            }
            
            public int postDec$lastReturned() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postDec$lastReturned();
            }
            
            public int get$size() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).get$size();
            }
            
            public int set$size(int val) {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).set$size(val);
            }
            
            public int postInc$size() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postInc$size();
            }
            
            public int postDec$size() {
                return ((fabric.util.AbstractList.AbstractListIterator._Impl)
                          fetch()).postDec$size();
            }
            
            public boolean hasNext() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).hasNext();
            }
            
            public boolean hasPrevious() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).hasPrevious();
            }
            
            public fabric.lang.Object next() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).next();
            }
            
            public fabric.lang.Object previous() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).previous();
            }
            
            public int nextIndex() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).nextIndex();
            }
            
            public int previousIndex() {
                return ((fabric.util.AbstractList.AbstractListIterator)
                          fetch()).previousIndex();
            }
            
            public void remove() {
                ((fabric.util.AbstractList.AbstractListIterator) fetch()).
                  remove();
            }
            
            public void set(fabric.lang.Object arg1) {
                ((fabric.util.AbstractList.AbstractListIterator) fetch()).
                  set(arg1);
            }
            
            public void add(fabric.lang.Object arg1) {
                ((fabric.util.AbstractList.AbstractListIterator) fetch()).
                  add(arg1);
            }
            
            public _Proxy(AbstractListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements AbstractListIterator {
            public fabric.util.AbstractList get$out$() { return this.out$; }
            
            private fabric.util.AbstractList out$;
            
            public int get$index() { return this.index; }
            
            public int set$index(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.index = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$index() {
                int tmp = this.get$index();
                this.set$index((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$index() {
                int tmp = this.get$index();
                this.set$index((int) (tmp - 1));
                return tmp;
            }
            
            private int index;
            
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
            
            private int knownMod;
            
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
            
            private int position;
            
            public int get$lastReturned() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.lastReturned;
            }
            
            public int set$lastReturned(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.lastReturned = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$lastReturned() {
                int tmp = this.get$lastReturned();
                this.set$lastReturned((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$lastReturned() {
                int tmp = this.get$lastReturned();
                this.set$lastReturned((int) (tmp - 1));
                return tmp;
            }
            
            private int lastReturned = -1;
            
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
            
            private int size;
            
            /**
     * @param index
     */
            private AbstractListIterator
              fabric$util$AbstractList$AbstractListIterator$(int index) {
                this.set$index((int) index);
                fabric$lang$Object$();
                this.set$knownMod((int) this.get$out$().get$modCount());
                this.set$position((int) index);
                this.set$size((int) this.get$out$().size());
                return (AbstractListIterator) this.$getProxy();
            }
            
            /**
     * Checks for modifications made to the list from
     * elsewhere while iteration is in progress.
     *
     * @throws ConcurrentModificationException if the
     *         list has been modified elsewhere.
     */
            private void checkMod() {
                if (this.get$knownMod() != this.get$out$().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
            }
            
            /**
     * Tests to see if there are any more objects to
     * return.
     *
     * @return True if the end of the list has not yet been
     *         reached.
     */
            public boolean hasNext() {
                return this.get$position() < this.get$size();
            }
            
            /**
     * Tests to see if there are objects prior to the
     * current position in the list.
     *
     * @return True if objects exist prior to the current
     *         position of the iterator.
     */
            public boolean hasPrevious() { return this.get$position() > 0; }
            
            /**
     * Retrieves the next object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException if there are no
     *         more objects to retrieve.
     * @throws ConcurrentModificationException if the
     *         list has been modified elsewhere.
     */
            public fabric.lang.Object next() {
                ((fabric.util.AbstractList.AbstractListIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$position() == this.get$size())
                    throw new fabric.util.NoSuchElementException();
                this.set$lastReturned((int) this.get$position());
                return this.get$out$().get(this.postInc$position());
            }
            
            /**
     * Retrieves the previous object from the list.
     *
     * @return The next object.
     * @throws NoSuchElementException if there are no
     *         previous objects to retrieve.
     * @throws ConcurrentModificationException if the
     *         list has been modified elsewhere.
     */
            public fabric.lang.Object previous() {
                ((fabric.util.AbstractList.AbstractListIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$position() == 0)
                    throw new fabric.util.NoSuchElementException();
                this.set$lastReturned((int)
                                        this.set$position(this.get$position() -
                                                              1));
                return this.get$out$().get(this.get$lastReturned());
            }
            
            /**
     * Returns the index of the next element in the
     * list, which will be retrieved by <code>next()</code>
     *
     * @return The index of the next element.
     */
            public int nextIndex() { return this.get$position(); }
            
            /**
     * Returns the index of the previous element in the
     * list, which will be retrieved by <code>previous()</code>
     *
     * @return The index of the previous element.
     */
            public int previousIndex() { return this.get$position() - 1; }
            
            /**
    * Removes the last object retrieved by <code>next()</code>
    * or <code>previous()</code> from the list, if the list
    * supports object removal.
    *
    * @throws IllegalStateException if the iterator is positioned
    *         before the start of the list or the last object has already
    *         been removed.
    * @throws UnsupportedOperationException if the list does
    *         not support removing elements.
    * @throws ConcurrentModificationException if the list
    *         has been modified elsewhere.
    */
            public void remove() {
                ((fabric.util.AbstractList.AbstractListIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$lastReturned() < 0)
                    throw new java.lang.IllegalStateException();
                this.get$out$().remove(this.get$lastReturned());
                this.postDec$size();
                this.set$position((int) this.get$lastReturned());
                this.set$lastReturned((int) -1);
                this.set$knownMod((int) this.get$out$().get$modCount());
            }
            
            /**
    * Replaces the last object retrieved by <code>next()</code>
    * or <code>previous</code> with o, if the list supports object
    * replacement and an add or remove operation has not already
    * been performed.
    *
    * @throws IllegalStateException if the iterator is positioned
    *         before the start of the list or the last object has already
    *         been removed.
    * @throws UnsupportedOperationException if the list doesn't support
    *         the addition or removal of elements.
    * @throws ClassCastException if the type of o is not a valid type
    *         for this list.
    * @throws IllegalArgumentException if something else related to o
    *         prevents its addition.
    * @throws ConcurrentModificationException if the list
    *         has been modified elsewhere.
    */
            public void set(fabric.lang.Object o) {
                ((fabric.util.AbstractList.AbstractListIterator._Impl)
                   this.fetch()).checkMod();
                if (this.get$lastReturned() < 0)
                    throw new java.lang.IllegalStateException();
                this.get$out$().set(this.get$lastReturned(), o);
            }
            
            /**
     * Adds the supplied object before the element that would be returned
     * by a call to <code>next()</code>, if the list supports addition.
     *
     * @param o The object to add to the list.
     * @throws UnsupportedOperationException if the list doesn't support
     *         the addition of new elements.
     * @throws ClassCastException if the type of o is not a valid type
     *         for this list.
     * @throws IllegalArgumentException if something else related to o
     *         prevents its addition.
     * @throws ConcurrentModificationException if the list
     *         has been modified elsewhere.
     */
            public void add(fabric.lang.Object o) {
                ((fabric.util.AbstractList.AbstractListIterator._Impl)
                   this.fetch()).checkMod();
                this.get$out$().add(this.postInc$position(), o);
                this.postInc$size();
                this.set$lastReturned((int) -1);
                this.set$knownMod((int) this.get$out$().get$modCount());
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (AbstractListIterator) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.util.AbstractList out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList.AbstractListIterator._Proxy(
                         this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                out.writeInt(this.index);
                out.writeInt(this.knownMod);
                out.writeInt(this.position);
                out.writeInt(this.lastReturned);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.out$ = (fabric.util.AbstractList)
                              $readRef(fabric.util.AbstractList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
                this.index = in.readInt();
                this.knownMod = in.readInt();
                this.position = in.readInt();
                this.lastReturned = in.readInt();
                this.size = in.readInt();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractList.AbstractListIterator._Impl src =
                  (fabric.util.AbstractList.AbstractListIterator._Impl) other;
                this.out$ = src.out$;
                this.index = src.index;
                this.knownMod = src.knownMod;
                this.position = src.position;
                this.lastReturned = src.lastReturned;
                this.size = src.size;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.AbstractListIterator._Static {
                public _Proxy(fabric.util.AbstractList.AbstractListIterator.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.
                  AbstractListIterator._Static $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      AbstractListIterator.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractList.
                        AbstractListIterator.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractList.AbstractListIterator._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.AbstractList.AbstractListIterator._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.AbstractListIterator._Static {
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
                    return new fabric.util.AbstractList.AbstractListIterator.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -35, 68, -28, 108,
        -11, 99, -124, 74, -83, -90, -92, 32, 55, -109, 24, -57, 10, -10, 99,
        96, 124, 103, 68, 47, -12, 11, 99, -87, -78, 16, -32, -3 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * This class follows the implementation requirements set forth in
   * {@link AbstractList#subList(int, int)}. It matches Sun's implementation
   * by using a non-public top-level class in the same package.
   *
   * @author Original author unknown
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface SubList extends fabric.util.AbstractList {
        public SubList fabric$util$AbstractList$SubList$();
        
        /**
     *
     */
        public static interface SubListIterator
          extends fabric.util.ListIterator, fabric.lang.Object {
            public fabric.util.AbstractList.SubList get$out$();
            
            public int get$index();
            
            public int set$index(int val);
            
            public int postInc$index();
            
            public int postDec$index();
            
            public fabric.util.ListIterator get$i();
            
            public fabric.util.ListIterator set$i(fabric.util.ListIterator val);
            
            public int get$position();
            
            public int set$position(int val);
            
            public int postInc$position();
            
            public int postDec$position();
            
            /**
       * Tests to see if there are any more objects to
       * return.
       *
       * @return True if the end of the list has not yet been
       *         reached.
       */
            public boolean hasNext();
            
            /**
       * Tests to see if there are objects prior to the
       * current position in the list.
       *
       * @return True if objects exist prior to the current
       *         position of the iterator.
       */
            public boolean hasPrevious();
            
            /**
       * Retrieves the next object from the list.
       *
       * @return The next object.
       * @throws NoSuchElementException if there are no
       *         more objects to retrieve.
       * @throws ConcurrentModificationException if the
       *         list has been modified elsewhere.
       */
            public fabric.lang.Object next();
            
            /**
       * Retrieves the previous object from the list.
       *
       * @return The next object.
       * @throws NoSuchElementException if there are no
       *         previous objects to retrieve.
       * @throws ConcurrentModificationException if the
       *         list has been modified elsewhere.
       */
            public fabric.lang.Object previous();
            
            /**
       * Returns the index of the next element in the
       * list, which will be retrieved by <code>next()</code>
       *
       * @return The index of the next element.
       */
            public int nextIndex();
            
            /**
       * Returns the index of the previous element in the
       * list, which will be retrieved by <code>previous()</code>
       *
       * @return The index of the previous element.
       */
            public int previousIndex();
            
            /**
       * Removes the last object retrieved by <code>next()</code>
       * from the list, if the list supports object removal.
       *
       * @throws IllegalStateException if the iterator is positioned
       *         before the start of the list or the last object has already
       *         been removed.
       * @throws UnsupportedOperationException if the list does
       *         not support removing elements.
       */
            public void remove();
            
            /**
      * Replaces the last object retrieved by <code>next()</code>
      * or <code>previous</code> with o, if the list supports object
      * replacement and an add or remove operation has not already
      * been performed.
      *
      * @throws IllegalStateException if the iterator is positioned
      *         before the start of the list or the last object has already
      *         been removed.
      * @throws UnsupportedOperationException if the list doesn't support
      *         the addition or removal of elements.
      * @throws ClassCastException if the type of o is not a valid type
      *         for this list.
      * @throws IllegalArgumentException if something else related to o
      *         prevents its addition.
      * @throws ConcurrentModificationException if the list
      *         has been modified elsewhere.
      */
            public void set(fabric.lang.Object o);
            
            /**
       * Adds the supplied object before the element that would be returned
       * by a call to <code>next()</code>, if the list supports addition.
       *
       * @param o The object to add to the list.
       * @throws UnsupportedOperationException if the list doesn't support
       *         the addition of new elements.
       * @throws ClassCastException if the type of o is not a valid type
       *         for this list.
       * @throws IllegalArgumentException if something else related to o
       *         prevents its addition.
       * @throws ConcurrentModificationException if the list
       *         has been modified elsewhere.
       */
            public void add(fabric.lang.Object o);
            
            public fabric.lang.Object $initLabels();
            
            public static class _Proxy extends fabric.lang.Object._Proxy
              implements SubListIterator {
                public fabric.util.AbstractList.SubList get$out$() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).get$out$();
                }
                
                public int get$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).get$index();
                }
                
                public int set$index(int val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).set$index(val);
                }
                
                public int postInc$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).postInc$index();
                }
                
                public int postDec$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).postDec$index();
                }
                
                public fabric.util.ListIterator get$i() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).get$i();
                }
                
                public fabric.util.ListIterator set$i(
                  fabric.util.ListIterator val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).set$i(val);
                }
                
                public int get$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).get$position();
                }
                
                public int set$position(int val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).set$position(val);
                }
                
                public int postInc$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).postInc$position();
                }
                
                public int postDec$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl) fetch()).postDec$position();
                }
                
                public boolean hasNext() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).hasNext();
                }
                
                public boolean hasPrevious() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).hasPrevious();
                }
                
                public fabric.lang.Object next() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).next();
                }
                
                public fabric.lang.Object previous() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).previous();
                }
                
                public int nextIndex() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).nextIndex();
                }
                
                public int previousIndex() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator)
                              fetch()).previousIndex();
                }
                
                public void remove() {
                    ((fabric.util.AbstractList.SubList.SubListIterator)
                       fetch()).remove();
                }
                
                public void set(fabric.lang.Object arg1) {
                    ((fabric.util.AbstractList.SubList.SubListIterator)
                       fetch()).set(arg1);
                }
                
                public void add(fabric.lang.Object arg1) {
                    ((fabric.util.AbstractList.SubList.SubListIterator)
                       fetch()).add(arg1);
                }
                
                public _Proxy(SubListIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static final class _Impl extends fabric.lang.Object._Impl
              implements SubListIterator {
                public fabric.util.AbstractList.SubList get$out$() {
                    return this.out$;
                }
                
                private SubList out$;
                
                public int get$index() { return this.index; }
                
                public int set$index(int val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean transactionCreated = tm.registerWrite(this);
                    this.index = val;
                    if (transactionCreated) tm.commitTransaction();
                    return val;
                }
                
                public int postInc$index() {
                    int tmp = this.get$index();
                    this.set$index((int) (tmp + 1));
                    return tmp;
                }
                
                public int postDec$index() {
                    int tmp = this.get$index();
                    this.set$index((int) (tmp - 1));
                    return tmp;
                }
                
                private int index;
                
                public fabric.util.ListIterator get$i() { return this.i; }
                
                public fabric.util.ListIterator set$i(
                  fabric.util.ListIterator val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean transactionCreated = tm.registerWrite(this);
                    this.i = val;
                    if (transactionCreated) tm.commitTransaction();
                    return val;
                }
                
                private fabric.util.ListIterator i;
                
                public int get$position() {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerRead(this);
                    return this.position;
                }
                
                public int set$position(int val) {
                    fabric.worker.transaction.TransactionManager tm =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
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
                
                private int position;
                
                /**
       * @param index
       */
                private SubListIterator
                  fabric$util$AbstractList$SubListIterator$(int index) {
                    this.set$index((int) index);
                    this.
                      set$i(
                        this.
                            get$out$().
                            get$backingList().
                            listIterator(
                              fabric.util.AbstractList._Static._Proxy.$instance.
                                  get$LOCAL_STORE(),
                              index + this.get$out$().get$offset()));
                    fabric$lang$Object$();
                    this.set$position((int) index);
                    return (SubListIterator) this.$getProxy();
                }
                
                /**
       * Tests to see if there are any more objects to
       * return.
       *
       * @return True if the end of the list has not yet been
       *         reached.
       */
                public boolean hasNext() {
                    return this.get$position() < this.get$out$().get$size();
                }
                
                /**
       * Tests to see if there are objects prior to the
       * current position in the list.
       *
       * @return True if objects exist prior to the current
       *         position of the iterator.
       */
                public boolean hasPrevious() { return this.get$position() > 0; }
                
                /**
       * Retrieves the next object from the list.
       *
       * @return The next object.
       * @throws NoSuchElementException if there are no
       *         more objects to retrieve.
       * @throws ConcurrentModificationException if the
       *         list has been modified elsewhere.
       */
                public fabric.lang.Object next() {
                    if (this.get$position() == this.get$out$().get$size())
                        throw new fabric.util.NoSuchElementException();
                    this.postInc$position();
                    return this.get$i().next();
                }
                
                /**
       * Retrieves the previous object from the list.
       *
       * @return The next object.
       * @throws NoSuchElementException if there are no
       *         previous objects to retrieve.
       * @throws ConcurrentModificationException if the
       *         list has been modified elsewhere.
       */
                public fabric.lang.Object previous() {
                    if (this.get$position() == 0)
                        throw new fabric.util.NoSuchElementException();
                    this.postDec$position();
                    return this.get$i().previous();
                }
                
                /**
       * Returns the index of the next element in the
       * list, which will be retrieved by <code>next()</code>
       *
       * @return The index of the next element.
       */
                public int nextIndex() {
                    return this.get$i().nextIndex() -
                      this.get$out$().get$offset();
                }
                
                /**
       * Returns the index of the previous element in the
       * list, which will be retrieved by <code>previous()</code>
       *
       * @return The index of the previous element.
       */
                public int previousIndex() {
                    return this.get$i().previousIndex() -
                      this.get$out$().get$offset();
                }
                
                /**
       * Removes the last object retrieved by <code>next()</code>
       * from the list, if the list supports object removal.
       *
       * @throws IllegalStateException if the iterator is positioned
       *         before the start of the list or the last object has already
       *         been removed.
       * @throws UnsupportedOperationException if the list does
       *         not support removing elements.
       */
                public void remove() {
                    this.get$i().remove();
                    this.get$out$().postDec$size();
                    this.set$position((int) nextIndex());
                    this.get$out$().set$modCount(
                                      (int)
                                        this.get$out$().get$backingList(
                                                          ).get$modCount());
                }
                
                /**
      * Replaces the last object retrieved by <code>next()</code>
      * or <code>previous</code> with o, if the list supports object
      * replacement and an add or remove operation has not already
      * been performed.
      *
      * @throws IllegalStateException if the iterator is positioned
      *         before the start of the list or the last object has already
      *         been removed.
      * @throws UnsupportedOperationException if the list doesn't support
      *         the addition or removal of elements.
      * @throws ClassCastException if the type of o is not a valid type
      *         for this list.
      * @throws IllegalArgumentException if something else related to o
      *         prevents its addition.
      * @throws ConcurrentModificationException if the list
      *         has been modified elsewhere.
      */
                public void set(fabric.lang.Object o) { this.get$i().set(o); }
                
                /**
       * Adds the supplied object before the element that would be returned
       * by a call to <code>next()</code>, if the list supports addition.
       *
       * @param o The object to add to the list.
       * @throws UnsupportedOperationException if the list doesn't support
       *         the addition of new elements.
       * @throws ClassCastException if the type of o is not a valid type
       *         for this list.
       * @throws IllegalArgumentException if something else related to o
       *         prevents its addition.
       * @throws ConcurrentModificationException if the list
       *         has been modified elsewhere.
       */
                public void add(fabric.lang.Object o) {
                    this.get$i().add(o);
                    this.get$out$().postInc$size();
                    this.postInc$position();
                    this.get$out$().set$modCount(
                                      (int)
                                        this.get$out$().get$backingList(
                                                          ).get$modCount());
                }
                
                public fabric.lang.Object $initLabels() {
                    this.set$$updateLabel(
                           fabric.lang.security.LabelUtil._Impl.noComponents());
                    this.set$$accessPolicy(
                           fabric.lang.security.LabelUtil._Impl.bottomConf());
                    return (SubListIterator) this.$getProxy();
                }
                
                public _Impl(fabric.worker.Store $location,
                             final SubList out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractList.SubList.SubListIterator.
                             _Proxy(this);
                }
                
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                    $writeRef($getStore(), this.out$, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                    out.writeInt(this.index);
                    $writeRef($getStore(), this.i, refTypes, out,
                              intraStoreRefs, interStoreRefs);
                    out.writeInt(this.position);
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
                    this.out$ =
                      (fabric.util.AbstractList.SubList)
                        $readRef(fabric.util.AbstractList.SubList._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                    this.index = in.readInt();
                    this.i = (fabric.util.ListIterator)
                               $readRef(fabric.util.ListIterator._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
                    this.position = in.readInt();
                }
                
                public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                    super.$copyAppStateFrom(other);
                    fabric.util.AbstractList.SubList.SubListIterator._Impl src =
                      (fabric.util.AbstractList.SubList.SubListIterator._Impl)
                        other;
                    this.out$ = src.out$;
                    this.index = src.index;
                    this.i = src.i;
                    this.position = src.position;
                }
            }
            
            interface _Static extends fabric.lang.Object, Cloneable {
                final class _Proxy
                extends fabric.
                  lang.
                  Object.
                  _Proxy
                  implements fabric.util.AbstractList.SubList.SubListIterator.
                               _Static
                {
                    public _Proxy(fabric.util.AbstractList.SubList.
                                    SubListIterator._Static._Impl impl) {
                        super(impl);
                    }
                    
                    public _Proxy(fabric.worker.Store store, long onum) {
                        super(store, onum);
                    }
                    
                    public static final fabric.util.AbstractList.SubList.
                      SubListIterator._Static $instance;
                    
                    static {
                        fabric.
                          util.
                          AbstractList.
                          SubList.
                          SubListIterator.
                          _Static.
                          _Impl
                          impl =
                          (fabric.
                            util.
                            AbstractList.
                            SubList.
                            SubListIterator.
                            _Static.
                            _Impl)
                            fabric.lang.Object._Static._Proxy.
                            $makeStaticInstance(
                              fabric.util.AbstractList.SubList.SubListIterator.
                                _Static._Impl.class);
                        $instance =
                          (fabric.util.AbstractList.SubList.SubListIterator.
                            _Static) impl.$getProxy();
                        impl.$init();
                    }
                }
                
                class _Impl
                extends fabric.
                  lang.
                  Object.
                  _Impl
                  implements fabric.util.AbstractList.SubList.SubListIterator.
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
                        super(store, onum, version, associates, expiry,
                              labelStore, labelOnum, accessPolicyStore,
                              accessPolicyOnum, in, refTypes, intraStoreRefs,
                              interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.AbstractList.SubList.
                                 SubListIterator._Static._Proxy(this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
            public static final byte[] $classHash = new byte[] { -35, 68, -28,
            108, -11, 99, -124, 74, -83, -90, -92, 32, 55, -109, 24, -57, 10,
            -10, 99, 96, 124, 103, 68, 47, -12, 11, 99, -87, -78, 16, -32, -3 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1548260582000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "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";
        }
        
        public fabric.util.AbstractList get$backingList();
        
        public fabric.util.AbstractList set$backingList(
          fabric.util.AbstractList val);
        
        public int get$offset();
        
        public int set$offset(int val);
        
        public int postInc$offset();
        
        public int postDec$offset();
        
        public int get$size();
        
        public int set$size(int val);
        
        public int postInc$size();
        
        public int postDec$size();
        
        /**
     * Construct the sublist.
     *
     * @param backing the list this comes from
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     */
        public SubList fabric$util$AbstractList$SubList$(
          fabric.util.AbstractList backing, int fromIndex, int toIndex);
        
        /**
     * This method checks the two modCount fields to ensure that there has
     * not been a concurrent modification, returning if all is okay.
     *
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     */
        public void checkMod();
        
        /**
     * Specified by AbstractList.subList to return the private field size.
     *
     * @return the sublist size
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     */
        public int size();
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to modify
     * @param o the new value
     * @return the old value
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if the backing list does not
     *         support the set operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     * @throws ClassCastException if o cannot be added to the backing list due
     *         to its type
     * @throws IllegalArgumentException if o cannot be added to the backing list
     *         for some other reason
     */
        public fabric.lang.Object set(int index, fabric.lang.Object o);
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to get from
     * @return the object at that location
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
        public fabric.lang.Object get(int index);
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the index to insert at
     * @param o the object to add
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws UnsupportedOperationException if the backing list does not
     *         support the add operation.
     * @throws ClassCastException if o cannot be added to the backing list due
     *         to its type.
     * @throws IllegalArgumentException if o cannot be added to the backing
     *         list for some other reason.
     */
        public void add(int index, fabric.lang.Object o);
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the index to remove
     * @return the removed object
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     * @throws UnsupportedOperationException if the backing list does not
     *         support the remove operation
     */
        public fabric.lang.Object remove(int index);
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     * This does no bounds checking, as it assumes it will only be called
     * by trusted code like clear() which has already checked the bounds.
     *
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if the backing list does
     *         not support removing elements.
     */
        public void removeRange(int fromIndex, int toIndex);
        
        /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to insert at
     * @param c the collection to insert
     * @return true if this list was modified, in other words, c is non-empty
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws UnsupportedOperationException if this list does not support the
     *         addAll operation
     * @throws ClassCastException if some element of c cannot be added to this
     *         list due to its type
     * @throws IllegalArgumentException if some element of c cannot be added
     *         to this list for some other reason
     * @throws NullPointerException if the specified collection is null
     */
        public boolean addAll(int index, fabric.util.Collection c);
        
        /**
     * Specified by AbstractList.subList to return addAll(size, c).
     *
     * @param c the collection to insert
     * @return true if this list was modified, in other words, c is non-empty
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if this list does not support the
     *         addAll operation
     * @throws ClassCastException if some element of c cannot be added to this
     *         list due to its type
     * @throws IllegalArgumentException if some element of c cannot be added
     *         to this list for some other reason
     * @throws NullPointerException if the specified collection is null
     */
        public boolean addAll(fabric.util.Collection c);
        
        /**
     * Specified by AbstractList.subList to return listIterator(Store).
     *
     * @return an iterator over the sublist
     */
        public fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
     * Specified by AbstractList.subList to return a wrapper around the
     * backing list's iterator.
     *
     * @param index the start location of the iterator
     * @return a list iterator over the sublist
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if the value is out of range
     */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                     final int index);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.util.AbstractList._Proxy
          implements SubList {
            public fabric.util.AbstractList get$backingList() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  get$backingList();
            }
            
            public fabric.util.AbstractList set$backingList(
              fabric.util.AbstractList val) {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  set$backingList(val);
            }
            
            public int get$offset() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  get$offset();
            }
            
            public int set$offset(int val) {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  set$offset(val);
            }
            
            public int postInc$offset() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  postInc$offset();
            }
            
            public int postDec$offset() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  postDec$offset();
            }
            
            public int get$size() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  get$size();
            }
            
            public int set$size(int val) {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  set$size(val);
            }
            
            public int postInc$size() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  postInc$size();
            }
            
            public int postDec$size() {
                return ((fabric.util.AbstractList.SubList._Impl) fetch()).
                  postDec$size();
            }
            
            public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$() {
                return ((fabric.util.AbstractList.SubList) fetch()).
                  fabric$util$AbstractList$SubList$();
            }
            
            public fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$(fabric.util.AbstractList arg1,
                                                int arg2, int arg3) {
                return ((fabric.util.AbstractList.SubList) fetch()).
                  fabric$util$AbstractList$SubList$(arg1, arg2, arg3);
            }
            
            public void checkMod() {
                ((fabric.util.AbstractList.SubList) fetch()).checkMod();
            }
            
            public _Proxy(SubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractList._Impl
          implements SubList {
            public SubList fabric$util$AbstractList$SubList$() {
                fabric$util$AbstractList$();
                return (SubList) this.$getProxy();
            }
            
            public fabric.util.AbstractList get$backingList() {
                return this.backingList;
            }
            
            public fabric.util.AbstractList set$backingList(
              fabric.util.AbstractList val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.backingList = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.util.AbstractList backingList;
            
            public int get$offset() { return this.offset; }
            
            public int set$offset(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.offset = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$offset() {
                int tmp = this.get$offset();
                this.set$offset((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$offset() {
                int tmp = this.get$offset();
                this.set$offset((int) (tmp - 1));
                return tmp;
            }
            
            int offset;
            
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
            
            /** The size of the sublist. */
            int size;
            
            /**
     * Construct the sublist.
     *
     * @param backing the list this comes from
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     */
            public SubList fabric$util$AbstractList$SubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex) {
                this.set$backingList(backing);
                this.set$offset((int) fromIndex);
                fabric$lang$Object$();
                this.set$modCount((int) backing.get$modCount());
                this.set$size((int) (toIndex - fromIndex));
                return (SubList) this.$getProxy();
            }
            
            /**
     * This method checks the two modCount fields to ensure that there has
     * not been a concurrent modification, returning if all is okay.
     *
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     */
            public void checkMod() {
                if (this.get$modCount() !=
                      this.get$backingList().get$modCount())
                    throw new fabric.util.ConcurrentModificationException();
            }
            
            /**
     * This method checks that a value is between 0 and size (inclusive). If
     * it is not, an exception is thrown.
     *
     * @param index the value to check
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     */
            private void checkBoundsInclusive(int index) {
                if (index < 0 || index > this.get$size())
                    throw new java.lang.IndexOutOfBoundsException(
                            "Index: " + index + ", Size:" + this.get$size());
            }
            
            /**
     * This method checks that a value is between 0 (inclusive) and size
     * (exclusive). If it is not, an exception is thrown.
     *
     * @param index the value to check
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
            private void checkBoundsExclusive(int index) {
                if (index < 0 || index >= this.get$size())
                    throw new java.lang.IndexOutOfBoundsException(
                            "Index: " + index + ", Size:" + this.get$size());
            }
            
            /**
     * Specified by AbstractList.subList to return the private field size.
     *
     * @return the sublist size
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     */
            public int size() {
                checkMod();
                return this.get$size();
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to modify
     * @param o the new value
     * @return the old value
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if the backing list does not
     *         support the set operation
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     * @throws ClassCastException if o cannot be added to the backing list due
     *         to its type
     * @throws IllegalArgumentException if o cannot be added to the backing list
     *         for some other reason
     */
            public fabric.lang.Object set(int index, fabric.lang.Object o) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsExclusive(index);
                return this.get$backingList().set(index + this.get$offset(), o);
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to get from
     * @return the object at that location
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
            public fabric.lang.Object get(int index) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsExclusive(index);
                return this.get$backingList().get(index + this.get$offset());
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the index to insert at
     * @param o the object to add
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws UnsupportedOperationException if the backing list does not
     *         support the add operation.
     * @throws ClassCastException if o cannot be added to the backing list due
     *         to its type.
     * @throws IllegalArgumentException if o cannot be added to the backing
     *         list for some other reason.
     */
            public void add(int index, fabric.lang.Object o) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsInclusive(index);
                this.get$backingList().add(index + this.get$offset(), o);
                this.postInc$size();
                this.set$modCount((int) this.get$backingList().get$modCount());
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the index to remove
     * @return the removed object
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     * @throws UnsupportedOperationException if the backing list does not
     *         support the remove operation
     */
            public fabric.lang.Object remove(int index) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsExclusive(index);
                fabric.lang.Object o =
                  this.get$backingList().remove(index + this.get$offset());
                this.postDec$size();
                this.set$modCount((int) this.get$backingList().get$modCount());
                return o;
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     * This does no bounds checking, as it assumes it will only be called
     * by trusted code like clear() which has already checked the bounds.
     *
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if the backing list does
     *         not support removing elements.
     */
            public void removeRange(int fromIndex, int toIndex) {
                checkMod();
                this.get$backingList().removeRange(this.get$offset() +
                                                       fromIndex,
                                                   this.get$offset() + toIndex);
                this.set$size((int) (this.get$size() - (toIndex - fromIndex)));
                this.set$modCount((int) this.get$backingList().get$modCount());
            }
            
            /**
     * Specified by AbstractList.subList to delegate to the backing list.
     *
     * @param index the location to insert at
     * @param c the collection to insert
     * @return true if this list was modified, in other words, c is non-empty
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
     * @throws UnsupportedOperationException if this list does not support the
     *         addAll operation
     * @throws ClassCastException if some element of c cannot be added to this
     *         list due to its type
     * @throws IllegalArgumentException if some element of c cannot be added
     *         to this list for some other reason
     * @throws NullPointerException if the specified collection is null
     */
            public boolean addAll(int index, fabric.util.Collection c) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsInclusive(index);
                int csize = c.size();
                boolean result =
                  this.get$backingList().addAll(this.get$offset() + index, c);
                this.set$size((int) (this.get$size() + csize));
                this.set$modCount((int) this.get$backingList().get$modCount());
                return result;
            }
            
            /**
     * Specified by AbstractList.subList to return addAll(size, c).
     *
     * @param c the collection to insert
     * @return true if this list was modified, in other words, c is non-empty
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws UnsupportedOperationException if this list does not support the
     *         addAll operation
     * @throws ClassCastException if some element of c cannot be added to this
     *         list due to its type
     * @throws IllegalArgumentException if some element of c cannot be added
     *         to this list for some other reason
     * @throws NullPointerException if the specified collection is null
     */
            public boolean addAll(fabric.util.Collection c) {
                return addAll(this.get$size(), c);
            }
            
            /**
     * Specified by AbstractList.subList to return listIterator(Store).
     *
     * @return an iterator over the sublist
     */
            public fabric.util.Iterator iterator(fabric.worker.Store store) {
                return listIterator(store);
            }
            
            /**
     * Specified by AbstractList.subList to return a wrapper around the
     * backing list's iterator.
     *
     * @param index the start location of the iterator
     * @return a list iterator over the sublist
     * @throws ConcurrentModificationException if the backing list has been
     *         modified externally to this sublist
     * @throws IndexOutOfBoundsException if the value is out of range
     */
            public fabric.util.ListIterator listIterator(
              fabric.worker.Store store, final int index) {
                checkMod();
                ((fabric.util.AbstractList.SubList._Impl) this.fetch()).
                  checkBoundsInclusive(index);
                return (SubListIterator)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              ((fabric.util.AbstractList.SubList.SubListIterator)
                                 new fabric.util.AbstractList.SubList.
                                   SubListIterator._Impl(
                                   store,
                                   (fabric.util.AbstractList.SubList)
                                     this.$getProxy()).
                                 $getProxy()).fetch()).
                               fabric$util$AbstractList$SubListIterator$(
                                 index));
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (SubList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList.SubList._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.backingList, refTypes, out,
                          intraStoreRefs, interStoreRefs);
                out.writeInt(this.offset);
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
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.backingList =
                  (fabric.util.AbstractList)
                    $readRef(fabric.util.AbstractList._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.offset = in.readInt();
                this.size = in.readInt();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.util.AbstractList.SubList._Impl src =
                  (fabric.util.AbstractList.SubList._Impl) other;
                this.backingList = src.backingList;
                this.offset = src.offset;
                this.size = src.size;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.SubList._Static {
                public _Proxy(fabric.util.AbstractList.SubList._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.SubList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      SubList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.util.AbstractList.SubList._Static._Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractList.SubList._Static._Impl.class);
                    $instance = (fabric.util.AbstractList.SubList._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.SubList._Static {
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
                    return new fabric.util.AbstractList.SubList._Static._Proxy(
                             this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 102, 15, 120, 93,
        69, -115, 9, 117, -17, -83, -50, 37, 47, 31, 110, -16, -18, 31, -38, 43,
        -82, -92, 20, 35, -89, 52, -43, 71, 13, -60, 110, -97 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    /**
   * This class is a RandomAccess version of SubList, as required by
   * {@link AbstractList#subList(int, int)}.
   *
   * @author Eric Blake (ebb9@email.byu.edu)
   */
    public static interface RandomAccessSubList
      extends fabric.util.RandomAccess, SubList {
        /**
     * Construct the sublist.
     *
     * @param backing the list this comes from
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     */
        public RandomAccessSubList
          fabric$util$AbstractList$RandomAccessSubList$(
          fabric.util.AbstractList backing, int fromIndex, int toIndex);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy
        extends fabric.util.AbstractList.SubList._Proxy
          implements RandomAccessSubList {
            public fabric.util.AbstractList.RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList arg1, int arg2, int arg3) {
                return ((fabric.util.AbstractList.RandomAccessSubList) fetch()).
                  fabric$util$AbstractList$RandomAccessSubList$(arg1, arg2,
                                                                arg3);
            }
            
            public _Proxy(RandomAccessSubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.AbstractList.SubList._Impl
          implements RandomAccessSubList {
            /**
     * Construct the sublist.
     *
     * @param backing the list this comes from
     * @param fromIndex the lower bound, inclusive
     * @param toIndex the upper bound, exclusive
     */
            public RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex) {
                fabric$util$AbstractList$SubList$(backing, fromIndex, toIndex);
                return (RandomAccessSubList) this.$getProxy();
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (RandomAccessSubList) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList.RandomAccessSubList._Proxy(
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
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.RandomAccessSubList._Static {
                public _Proxy(fabric.util.AbstractList.RandomAccessSubList.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.
                  RandomAccessSubList._Static $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      RandomAccessSubList.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        util.
                        AbstractList.
                        RandomAccessSubList.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.util.AbstractList.RandomAccessSubList._Static.
                            _Impl.class);
                    $instance =
                      (fabric.util.AbstractList.RandomAccessSubList._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.util.AbstractList.RandomAccessSubList._Static {
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
                    return new fabric.util.AbstractList.RandomAccessSubList.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 65, -95, 124, -122,
        24, 17, 5, -19, 21, 85, -97, 41, -11, 109, 96, 92, -73, -2, 100, -11,
        -39, 88, 103, 10, -123, -12, -17, 12, -70, -54, -70, -77 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList {
        public int get$modCount() {
            return ((fabric.util.AbstractList._Impl) fetch()).get$modCount();
        }
        
        public int set$modCount(int val) {
            return ((fabric.util.AbstractList._Impl) fetch()).set$modCount(val);
        }
        
        public int postInc$modCount() {
            return ((fabric.util.AbstractList._Impl) fetch()).postInc$modCount(
                                                                );
        }
        
        public int postDec$modCount() {
            return ((fabric.util.AbstractList._Impl) fetch()).postDec$modCount(
                                                                );
        }
        
        public fabric.util.AbstractList fabric$util$AbstractList$() {
            return ((fabric.util.AbstractList) fetch()).
              fabric$util$AbstractList$();
        }
        
        public fabric.lang.Object get(int arg1) {
            return ((fabric.util.AbstractList) fetch()).get(arg1);
        }
        
        public void add(int arg1, fabric.lang.Object arg2) {
            ((fabric.util.AbstractList) fetch()).add(arg1, arg2);
        }
        
        public boolean addAll(int arg1, fabric.util.Collection arg2) {
            return ((fabric.util.AbstractList) fetch()).addAll(arg1, arg2);
        }
        
        public int indexOf(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractList) fetch()).indexOf(arg1);
        }
        
        public int lastIndexOf(fabric.lang.Object arg1) {
            return ((fabric.util.AbstractList) fetch()).lastIndexOf(arg1);
        }
        
        public fabric.util.ListIterator listIterator(fabric.worker.Store arg1) {
            return ((fabric.util.AbstractList) fetch()).listIterator(arg1);
        }
        
        public fabric.util.ListIterator listIterator(fabric.worker.Store arg1,
                                                     int arg2) {
            return ((fabric.util.AbstractList) fetch()).listIterator(arg1,
                                                                     arg2);
        }
        
        public fabric.lang.Object remove(int arg1) {
            return ((fabric.util.AbstractList) fetch()).remove(arg1);
        }
        
        public void removeRange(int arg1, int arg2) {
            ((fabric.util.AbstractList) fetch()).removeRange(arg1, arg2);
        }
        
        public fabric.lang.Object set(int arg1, fabric.lang.Object arg2) {
            return ((fabric.util.AbstractList) fetch()).set(arg1, arg2);
        }
        
        public fabric.util.List subList(int arg1, int arg2) {
            return ((fabric.util.AbstractList) fetch()).subList(arg1, arg2);
        }
        
        public _Proxy(AbstractList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractList {
        public int get$modCount() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.modCount;
        }
        
        public int set$modCount(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.modCount = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$modCount() {
            int tmp = this.get$modCount();
            this.set$modCount((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$modCount() {
            int tmp = this.get$modCount();
            this.set$modCount((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * A count of the number of structural modifications that have been made to
   * the list (that is, insertions and removals). Structural modifications
   * are ones which change the list size or affect how iterations would
   * behave. This field is available for use by Iterator and ListIterator,
   * in order to throw a {@link ConcurrentModificationException} in response
   * to the next operation on the iterator. This <i>fail-fast</i> behavior
   * saves the user from many subtle bugs otherwise possible from concurrent
   * modification during iteration.
   * <p>
   *
   * To make lists fail-fast, increment this field by just 1 in the
   * <code>add(int, Object)</code> and <code>remove(int)</code> methods.
   * Otherwise, this field may be ignored.
   */
        protected int modCount;
        
        /**
   * The main constructor, for use by subclasses.
   */
        public fabric.util.AbstractList fabric$util$AbstractList$() {
            fabric$util$AbstractCollection$();
            return (fabric.util.AbstractList) this.$getProxy();
        }
        
        /**
   * Returns the elements at the specified position in the list.
   *
   * @param index the element to return
   * @return the element at that position
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public abstract fabric.lang.Object get(int index);
        
        /**
   * Insert an element into the list at a given position (optional operation).
   * This shifts all existing elements from that position to the end one
   * index to the right.  This version of add has no return, since it is
   * assumed to always succeed if there is no exception. This implementation
   * always throws UnsupportedOperationException, and must be overridden to
   * make a modifiable List.  If you want fail-fast iterators, be sure to
   * increment modCount when overriding this.
   *
   * @param index the location to insert the item
   * @param o the object to insert
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason
   * @see #modCount
   */
        public void add(int index, fabric.lang.Object o) {
            throw new java.lang.UnsupportedOperationException();
        }
        
        /**
   * Add an element to the end of the list (optional operation). If the list
   * imposes restraints on what can be inserted, such as no null elements,
   * this should be documented. This implementation calls
   * <code>add(size(), o);</code>, and will fail if that version does.
   *
   * @param o the object to add
   * @return true, as defined by Collection for a modified list
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason
   * @see #add(int, Object)
   */
        public boolean add(fabric.lang.Object o) {
            add(size(), o);
            return true;
        }
        
        /**
   * Insert the contents of a collection into the list at a given position
   * (optional operation). Shift all elements at that position to the right
   * by the number of elements inserted. This operation is undefined if
   * this list is modified during the operation (for example, if you try
   * to insert a list into itself). This implementation uses the iterator of
   * the collection, repeatedly calling add(int, Object); this will fail
   * if add does. This can often be made more efficient.
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
   * @see #add(int, Object)
   */
        public boolean addAll(int index, fabric.util.Collection c) {
            fabric.util.Iterator
              itr =
              c.
              iterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int size = c.size();
            for (int pos = size; pos > 0; pos--) add(index++, itr.next());
            return size > 0;
        }
        
        /**
   * Clear the list, such that a subsequent call to isEmpty() would return
   * true (optional operation). This implementation calls
   * <code>removeRange(0, size())</code>, so it will fail unless remove
   * or removeRange is overridden.
   *
   * @throws UnsupportedOperationException if this list does not support the
   *         clear operation
   * @see #remove(int)
   * @see #removeRange(int, int)
   */
        public void clear() { removeRange(0, size()); }
        
        /**
   * Test whether this list is equal to another object. A List is defined to be
   * equal to an object if and only if that object is also a List, and the two
   * lists have the same sequence. Two lists l1 and l2 are equal if and only
   * if <code>l1.size() == l2.size()</code>, and for every integer n between 0
   * and <code>l1.size() - 1</code> inclusive, <code>l1.get(n) == null ?
   * l2.get(n) == null : l1.get(n).equals(l2.get(n))</code>.
   * <p>
   *
   * This implementation returns true if the object is this, or false if the
   * object is not a List.  Otherwise, it iterates over both lists (with
   * iterator(Store)), returning false if two elements compare false or one list
   * is shorter, and true if the iteration completes successfully.
   *
   * @param o the object to test for equality with this list
   * @return true if o is equal to this list
   * @see Object#equals(Object)
   * @see #hashCode()
   */
        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o,
                                                   (fabric.util.AbstractList)
                                                     this.$getProxy()))
                return true;
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.
                        $unwrap(o)) instanceof fabric.util.List)) return false;
            int size = size();
            if (size !=
                  ((fabric.util.List) fabric.lang.Object._Proxy.$getProxy(o)).
                  size())
                return false;
            fabric.util.Iterator
              itr1 =
              iterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            fabric.util.Iterator
              itr2 =
              ((fabric.util.List)
                 fabric.lang.Object._Proxy.
                 $getProxy(o)).
              iterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            while (--size >= 0)
                if (!fabric.util.AbstractCollection._Impl.equals(itr1.next(),
                                                                 itr2.next()))
                    return false;
            return true;
        }
        
        /**
   * Obtains a hash code for this list. In order to obey the general
   * contract of the hashCode method of class Object, this value is
   * calculated as follows:
   *
<pre>hashCode = 1;
Iterator i = list.iterator(LOCAL_STORE);
while (i.hasNext())
{
  Object obj = i.next();
  hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
}</pre>
   *
   * This ensures that the general contract of Object.hashCode() is adhered to.
   *
   * @return the hash code of this list
   *
   * @see Object#hashCode()
   * @see #equals(Object)
   */
        public int hashCode() {
            int hashCode = 1;
            fabric.util.Iterator
              itr =
              iterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int pos = size();
            while (--pos >= 0)
                hashCode =
                  31 * hashCode +
                    fabric.util.AbstractCollection._Impl.hashCode(itr.next());
            return hashCode;
        }
        
        /**
   * Obtain the first index at which a given object is to be found in this
   * list. This implementation follows a listIterator(store) until a match is found,
   * or returns -1 if the list end is reached.
   *
   * @param o the object to search for
   * @return the least integer n such that <code>o == null ? get(n) == null :
   *         o.equals(get(n))</code>, or -1 if there is no such index
   */
        public int indexOf(fabric.lang.Object o) {
            fabric.util.ListIterator
              itr =
              listIterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int size = size();
            for (int pos = 0; pos < size; pos++)
                if (fabric.util.AbstractCollection._Impl.equals(o, itr.next()))
                    return pos;
            return -1;
        }
        
        /**
   * Obtain an Iterator over this list, whose sequence is the list order.
   * This implementation uses size(), get(int), and remove(int) of the
   * backing list, and does not support remove unless the list does. This
   * implementation is fail-fast if you correctly maintain modCount.
   * Also, this implementation is specified by Sun to be distinct from
   * listIterator, although you could easily implement it as
   * <code>return listIterator(0)</code>.
   *
   * @return an Iterator over the elements of this list, in order
   * @see #modCount
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store) {
            return (AnonymousIterator)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.AbstractList.AnonymousIterator)
                          new fabric.util.AbstractList.AnonymousIterator._Impl(
                            store, (fabric.util.AbstractList) this.$getProxy()).
                          $getProxy()).
                           fabric$util$AbstractList$AnonymousIterator$());
        }
        
        /**
   * Obtain the last index at which a given object is to be found in this
   * list. This implementation grabs listIterator(size()), then searches
   * backwards for a match or returns -1.
   *
   * @return the greatest integer n such that <code>o == null ? get(n) == null
   *         : o.equals(get(n))</code>, or -1 if there is no such index
   */
        public int lastIndexOf(fabric.lang.Object o) {
            int pos = size();
            fabric.util.ListIterator
              itr =
              listIterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                pos);
            while (--pos >= 0)
                if (fabric.util.AbstractCollection._Impl.equals(o,
                                                                itr.previous()))
                    return pos;
            return -1;
        }
        
        /**
   * Obtain a ListIterator over this list, starting at the beginning. This
   * implementation returns listIterator(0).
   *
   * @return a ListIterator over the elements of this list, in order, starting
   *         at the beginning
   */
        public fabric.util.ListIterator listIterator(
          fabric.worker.Store store) {
            return listIterator(store, 0);
        }
        
        /**
   * Obtain a ListIterator over this list, starting at a given position.
   * A first call to next() would return the same as get(index), and a
   * first call to previous() would return the same as get(index - 1).
   * <p>
   *
   * This implementation uses size(), get(int), set(int, Object),
   * add(int, Object), and remove(int) of the backing list, and does not
   * support remove, set, or add unless the list does. This implementation
   * is fail-fast if you correctly maintain modCount.
   *
   * @param index the position, between 0 and size() inclusive, to begin the
   *        iteration from
   * @return a ListIterator over the elements of this list, in order, starting
   *         at index
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @see #modCount
   */
        public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                     final int index) {
            if (index < 0 || index > size())
                throw new java.lang.IndexOutOfBoundsException("Index: " +
                                                                index +
                                                                ", Size:" +
                                                                size());
            return (AbstractListIterator)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.AbstractList.AbstractListIterator.
                          _Impl)
                          ((fabric.util.AbstractList.AbstractListIterator)
                             new fabric.util.AbstractList.AbstractListIterator.
                               _Impl(store,
                                     (fabric.util.AbstractList)
                                       this.$getProxy()).
                             $getProxy()).fetch()).
                           fabric$util$AbstractList$AbstractListIterator$(
                             index));
        }
        
        /**
   * Remove the element at a given position in this list (optional operation).
   * Shifts all remaining elements to the left to fill the gap. This
   * implementation always throws an UnsupportedOperationException.
   * If you want fail-fast iterators, be sure to increment modCount when
   * overriding this.
   *
   * @param index the position within the list of the object to remove
   * @return the object that was removed
   * @throws UnsupportedOperationException if this list does not support the
   *         remove operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   * @see #modCount
   */
        public fabric.lang.Object remove(int index) {
            throw new java.lang.UnsupportedOperationException();
        }
        
        /**
   * Remove a subsection of the list. This is called by the clear and
   * removeRange methods of the class which implements subList, which are
   * difficult for subclasses to override directly. Therefore, this method
   * should be overridden instead by the more efficient implementation, if one
   * exists. Overriding this can reduce quadratic efforts to constant time
   * in some cases!
   * <p>
   *
   * This implementation first checks for illegal or out of range arguments. It
   * then obtains a ListIterator over the list using listIterator(fromIndex).
   * It then calls next() and remove() on this iterator repeatedly, toIndex -
   * fromIndex times.
   *
   * @param fromIndex the index, inclusive, to remove from.
   * @param toIndex the index, exclusive, to remove to.
   * @throws UnsupportedOperationException if the list does
   *         not support removing elements.
   */
        public void removeRange(int fromIndex, int toIndex) {
            fabric.util.ListIterator
              itr =
              listIterator(
                fabric.util.AbstractList._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                fromIndex);
            for (int index = fromIndex; index < toIndex; index++) {
                itr.next();
                itr.remove();
            }
        }
        
        /**
   * Replace an element of this list with another object (optional operation).
   * This implementation always throws an UnsupportedOperationException.
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
   */
        public fabric.lang.Object set(int index, fabric.lang.Object o) {
            throw new java.lang.UnsupportedOperationException();
        }
        
        /**
   * Obtain a List view of a subsection of this list, from fromIndex
   * (inclusive) to toIndex (exclusive). If the two indices are equal, the
   * sublist is empty. The returned list should be modifiable if and only
   * if this list is modifiable. Changes to the returned list should be
   * reflected in this list. If this list is structurally modified in
   * any way other than through the returned list, the result of any subsequent
   * operations on the returned list is undefined.
   * <p>
   *
   * This implementation returns a subclass of AbstractList. It stores, in
   * private fields, the offset and size of the sublist, and the expected
   * modCount of the backing list. If the backing list implements RandomAccess,
   * the sublist will also.
   * <p>
   *
   * The subclass's <code>set(int, Object)</code>, <code>get(int)</code>,
   * <code>add(int, Object)</code>, <code>remove(int)</code>,
   * <code>addAll(int, Collection)</code> and
   * <code>removeRange(int, int)</code> methods all delegate to the
   * corresponding methods on the backing abstract list, after
   * bounds-checking the index and adjusting for the offset. The
   * <code>addAll(Collection c)</code> method merely returns addAll(size, c).
   * The <code>listIterator(Store, int)</code> method returns a "wrapper object"
   * over a list iterator on the backing list, which is created with the
   * corresponding method on the backing list. The <code>iterator(Store)</code>
   * method merely returns listIterator(store), and the <code>size()</code> method
   * merely returns the subclass's size field.
   * <p>
   *
   * All methods first check to see if the actual modCount of the backing
   * list is equal to its expected value, and throw a
   * ConcurrentModificationException if it is not.
   *
   * @param fromIndex the index that the returned list should start from
   *        (inclusive)
   * @param toIndex the index that the returned list should go to (exclusive)
   * @return a List backed by a subsection of this list
   * @throws IndexOutOfBoundsException if fromIndex &lt; 0
   *         || toIndex &gt; size()
   * @throws IllegalArgumentException if fromIndex &gt; toIndex
   * @see ConcurrentModificationException
   * @see RandomAccess
   */
        public fabric.util.List subList(int fromIndex, int toIndex) {
            if (fromIndex > toIndex)
                throw new java.lang.IllegalArgumentException(fromIndex + " > " +
                                                               toIndex);
            if (fromIndex < 0 || toIndex > size())
                throw new java.lang.IndexOutOfBoundsException();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        (fabric.util.AbstractList)
                          this.$getProxy(
                                 ))) instanceof fabric.util.RandomAccess)
                return (RandomAccessSubList)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.util.AbstractList.RandomAccessSubList)
                              new fabric.util.AbstractList.RandomAccessSubList.
                                _Impl(this.$getStore()).
                              $getProxy()).
                               fabric$util$AbstractList$RandomAccessSubList$(
                                 (fabric.util.AbstractList) this.$getProxy(),
                                 fromIndex, toIndex));
            return (SubList)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.util.AbstractList.SubList)
                          new fabric.util.AbstractList.SubList._Impl(
                            this.$getStore()).$getProxy()).
                           fabric$util$AbstractList$SubList$(
                             (fabric.util.AbstractList) this.$getProxy(),
                             fromIndex, toIndex));
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractList._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.modCount);
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
            this.modCount = in.readInt();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.AbstractList._Impl src =
              (fabric.util.AbstractList._Impl) other;
            this.modCount = src.modCount;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractList._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.AbstractList._Static._Impl) fetch()).
                  get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.AbstractList._Static._Impl) fetch()).
                  set$LOCAL_STORE(val);
            }
            
            public _Proxy(fabric.util.AbstractList._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractList._Static $instance;
            
            static {
                fabric.
                  util.
                  AbstractList.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.AbstractList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractList._Static._Impl.class);
                $instance = (fabric.util.AbstractList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList._Static {
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
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeInline(out, this.LOCAL_STORE);
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
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm527 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled530 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff528 = 1;
                        boolean $doBackoff529 = true;
                        boolean $retry523 = true;
                        boolean $keepReads524 = false;
                        $label521: for (boolean $commit522 = false; !$commit522;
                                        ) {
                            if ($backoffEnabled530) {
                                if ($doBackoff529) {
                                    if ($backoff528 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff528));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e525) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff528 < 5000) $backoff528 *= 2;
                                }
                                $doBackoff529 = $backoff528 <= 32 ||
                                                  !$doBackoff529;
                            }
                            $commit522 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.AbstractList._Static._Proxy.
                                      $instance.
                                      set$LOCAL_STORE(
                                        fabric.worker.Worker.getWorker().
                                            getLocalStore());
                                }
                                catch (final fabric.worker.
                                         RetryException $e525) {
                                    throw $e525;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e525) {
                                    throw $e525;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e525) {
                                    throw $e525;
                                }
                                catch (final Throwable $e525) {
                                    $tm527.getCurrentLog().checkRetrySignal();
                                    throw $e525;
                                }
                            }
                            catch (final fabric.worker.RetryException $e525) {
                                $commit522 = false;
                                continue $label521;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e525) {
                                $commit522 = false;
                                $retry523 = false;
                                $keepReads524 = $e525.keepReads;
                                if ($tm527.checkForStaleObjects()) {
                                    $retry523 = true;
                                    $keepReads524 = false;
                                    continue $label521;
                                }
                                fabric.common.TransactionID $currentTid526 =
                                  $tm527.getCurrentTid();
                                if ($e525.tid ==
                                      null ||
                                      !$e525.tid.isDescendantOf(
                                                   $currentTid526)) {
                                    throw $e525;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e525);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e525) {
                                $commit522 = false;
                                fabric.common.TransactionID $currentTid526 =
                                  $tm527.getCurrentTid();
                                if ($e525.tid.isDescendantOf($currentTid526))
                                    continue $label521;
                                if ($currentTid526.parent != null) {
                                    $retry523 = false;
                                    throw $e525;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e525) {
                                $commit522 = false;
                                if ($tm527.checkForStaleObjects())
                                    continue $label521;
                                $retry523 = false;
                                throw new fabric.worker.AbortException($e525);
                            }
                            finally {
                                if ($commit522) {
                                    fabric.common.TransactionID $currentTid526 =
                                      $tm527.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e525) {
                                        $commit522 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e525) {
                                        $commit522 = false;
                                        $retry523 = false;
                                        $keepReads524 = $e525.keepReads;
                                        if ($tm527.checkForStaleObjects()) {
                                            $retry523 = true;
                                            $keepReads524 = false;
                                            continue $label521;
                                        }
                                        if ($e525.tid ==
                                              null ||
                                              !$e525.tid.isDescendantOf(
                                                           $currentTid526))
                                            throw $e525;
                                        throw new fabric.worker.
                                                UserAbortException($e525);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e525) {
                                        $commit522 = false;
                                        $currentTid526 = $tm527.getCurrentTid();
                                        if ($currentTid526 != null) {
                                            if ($e525.tid.equals(
                                                            $currentTid526) ||
                                                  !$e525.tid.
                                                  isDescendantOf(
                                                    $currentTid526)) {
                                                throw $e525;
                                            }
                                        }
                                    }
                                } else if ($keepReads524) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit522) {
                                    {  }
                                    if ($retry523) { continue $label521; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -115, 10, 32, 27, -123,
    111, -19, -90, 67, 79, -13, 74, -3, 60, -44, -105, 72, -49, -7, -83, -92,
    57, 62, 36, -108, -115, -15, 89, 92, -22, -48, -72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
