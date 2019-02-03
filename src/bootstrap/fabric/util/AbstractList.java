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
        
        public static final byte[] $classHash = new byte[] { 21, 118, 115, -91,
        -43, -24, -105, -29, 39, -99, 117, 92, 28, 45, -76, 75, 113, 6, -68,
        -106, 24, -74, 31, 51, 112, -113, 49, 6, -55, 51, -69, 41 };
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
        
        public static final byte[] $classHash = new byte[] { 8, 121, -107, -110,
        -40, -14, 126, -63, 101, -117, 65, -12, -103, -55, 52, -49, 98, 37, 29,
        -89, 26, -4, -123, -40, -27, 99, 101, -32, 124, -115, -87, -120 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1ZfWwUxxV/d/42BhvzbYwBc6Hi605AVUGctoELH1cOcLBNhVFw9/bm7MV7u8funDkIpPlQCmkVqqSGghr4o6VJcCkkbVGrNkhETdJQEFXSj1CpTVEllzQEtWnSLylt+t7s3N3eJ7ZUJM/bm3nvzW/evPeb2eXsbaiyLWiPKRFN9/N9CWb71yuRULhTsWwWDeqKbXdjb586oTJ07J3nom1e8IahQVUM09BURe8zbA6TwruVISVgMB7o2Rbq2Al1KhluVOwBDt6da1MWzEuY+r5+3eRykgL/R5cEhr++q+l7FdDYC42a0cUVrqlB0+AsxXuhIc7iEWbZa6JRFu2FyQZj0S5maYqu7UdF0+iFZlvrNxSetJi9jdmmPkSKzXYywSwxZ7qT4JsI20qq3LQQfpMDP8k1PRDWbN4RhuqYxvSovQcegsowVMV0pR8Vp4fTqwgIj4H11I/q9RrCtGKKytImlYOaEeUwN98is2LfJlRA05o44wNmZqpKQ8EOaHYg6YrRH+jilmb0o2qVmcRZOLSUdIpKtQlFHVT6WR+Hmfl6nc4QatWJsJAJh2n5asIT7llL3p65duv2lnuOPGhsNLzgQcxRpuqEvxaN2vKMtrEYs5ihMsewYXH4mDL94mEvACpPy1N2dH544P17l7Zdet3RmV1EZ2tkN1N5n3o6MumN1uCi1RUEozZh2hqlQs7Kxa52ypGOVAKzfXrGIw3604OXtr224+ERdssL9SGoVk09Gcesmqya8YSmM2sDM5ilcBYNQR0zokExHoIafA5rBnN6t8ZiNuMhqNRFV7UpfmOIYuiCQlSDz5oRM9PPCYUPiOdUAgCa8A8qALwfAvQfQPkJgM4Eh3BgwIyzQERPsr2Y3gH8Y4qlDgSwbi1NXaaaiX0B21IDVtLgGmo6/c7i10Qw1xWVU2r7Ecf/21+K8Dft9XgwtHNVM8oiio37JHNmbaeOZbHR1KPM6lP1IxdDMOXiCZE3dZTrNuariIwH97o1nyXctsPJteveP9d3xck5spWB47DMwefspxufz/0jxGkDseQtaKDy8iNh+ZGwznpS/uCp0HdEFlXbotwyrhvQ9d0JXeEx04qnwOMR65wq7MV0uPmDSCrov2FR1wOf+8LhdtzAVGJvJW4lqfryqyjLPSF8UrA0+tTGQ+/84/yxg2a2njj4Csq80JLKtD0/aJapsijSYNb94nnKhb6LB31eopg6ZD+uYH4ilbTlz5FTrh1p6qNoVIVhAsVA0WkozVf1fMAy92Z7RDJMoqbZyQsKVh5AwZqf7kqcvH7tzyvFeZIm2EYXE3cx3uEqanLWKMp3cjb23RZjqPf7451fO3r70E4ReNRYUGxCH7VBLGZFJMHjr+/57R/ePv0rb3azONQkLG0IazwlFjP5Y/znwb//0h+VJnWQRIIOSlqYl+GFBE29MAsOGUJHlkLstq/HiJtRLaYpEZ1RqnzUeNfyC+8daXL2W8ceJ3oWLL2zg2z/rLXw8JVd/2wTbjwqnVDZAGbVHNqbkvW8xrKUfYQj9cibc078TDmJqY+kZWv7meAhj8xeAjUN6bRUedF4i9jnFUJ3mWiXU4iEBxBjn6Km3Ylpq+ivtgsPivV04mZTtjdw9pmW4GduOUyRSVnyMb8IU2xXXNW0YiT+d2979ateqOmFJnHYKwbfriDdYbb04nFtB2VnGCbmjOcevc4505Epydb8cnFNm18sWYbCZ9Km53qnPpz0wkA0U5BakeiXINFfl/IajU5JUDs15QHxcI8wWSDahdQsEoH00uNiDnVaPJ7klBxigiUcqvB6gXlcGOdOS4tjRQ3JA5kdHv7yx/4jw04mOreWBQUXB7eNc3MR80wUk9Es88vNIizW3zx/8CfPHzzknOrNuWfwOiMZ/+5v/nPVf/zG5SL8XoH3K4dNqF2VCV8DhW8WhmEZhu0DKf9UJHyh4uHziPClMv5Ezk6QfkalfNvlj0PtoGHuNTabUfp9X1lQAYD7J0npKQJqqwOKmnAhBLICR3b+OxeCmxNLQ2hHYyzF+1dI2VoEQndZCGQ1W8qpORAa8CrItzG8bBvsDpGYhg5WooOQlPcWgbGjLAyy+qyUq3JgCMYqOb24TXnQ0bsAz3N8ng9Q9UCR6XeVL66qmGYoerqwKvEW7qPnlWLOVHHbCmmL1wl6j6FfEceDiwaLE6z7quIQLNbXnFJ3cVFbpx8dPhXd+u3lXsm463Fi+cKUna2ByrTgRW+zeP3IcueNW3NWBwdH+50ynZs3bb72mc1nL29YqD7thYoMSRa88+QadeRSY70lsqg7hyDn5aZQL+7FKtz8y1I+6N7D7M6Xyh8y2S+l7TLNO7I8WUq4T3gdKnOmiWYPB7+zcz7aOd8db56+LFgjd4lUZlghXcelfGJ8SySTw1I+UnqJ7hV8scyYcLEfmUYdYOrgZvl6ulayPYl1WAhDphbNW8uE9Hm2HaD7NSkvlFhLARtj1iaSEV1T8xi5Xjr6gZTnxrbCJ8uMfZWaL+GNb0Cxt7AUL7bAmohp6kwxiq1xHkKJAPTclPLNMvv1ROFqyOQNKa+MbTUnyox9g5phDhNwNZ0WG9LMpNB7qhj0mTgvXvI+3yPlxvFBJ5MNUq4ZG/Rvlhk7Tc1JzCYjvQvIh82SD+kS5ncuYWJoVv77WKn0exRgxyYp7x7f+shktZQrx7a+82XGXqRmhA5t1748Wwx3C076FJLdJSlfHB9uMnlBypGx4f5RmbEfU/N9vFnSvoToLimIsRjwNpz1GYCdr0g5TuBk8oKUYwT+cpmxn1LzEoeJ6YCXB0/VcA5g1xIp28YHnkzmSDljbOB/XmbsKjWvIg9aLG4OiRPxsWKoacqrAJHHpNwzPtRkkpByd2nUrhPxWeH1l2Wg/5qaa3hXtxkvi/uPAOwlKUfGh5tMzkj5rXHg/l0Z3OJ6/xbiVqLRkrjn4qS3AGKalD3jw00m3VJuGVuWjJYZu0nNDaR7n2ZoPKxEmO7QSorD1GL3Dhpcide/2UU+z8nPxWrwFXZ6dNPSaSU+zc0s+IAv7c6daqydcarnLfFtKfMpuC4MtbGkrrtfgl3P1VicMU0sps55JU4I8R4uynUXxmOBhFjcu47GX7A4HA369VcRx5ZM42x3S9Ki/3Y4+8GMf1XXdt8Qn3jozK7dd/Tp63976GX2lTUfnrj8yV9E7przXMtHj18fVdmNA0+eOfw/OX4brA4ZAAA=";
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
            
            public static final byte[] $classHash = new byte[] { 8, 121, -107,
            -110, -40, -14, 126, -63, 101, -117, 65, -12, -103, -55, 52, -49,
            98, 37, 29, -89, 26, -4, -123, -40, -27, 99, 101, -32, 124, -115,
            -87, -120 };
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
        
        public static final byte[] $classHash = new byte[] { 19, -3, -12, -32,
        -95, 57, 19, -77, 37, 11, 93, 118, 106, 63, -102, 112, 91, 59, -116,
        -113, -50, 85, 77, -122, 90, 103, 84, 5, 127, 61, 108, -120 };
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
        
        public static final byte[] $classHash = new byte[] { -67, 80, 41, -75,
        89, 110, -6, -78, 122, 82, -55, 43, -81, 49, 27, -108, -101, 108, 81,
        58, 122, -33, -49, -44, 127, -57, -104, -42, -125, 123, -64, -107 };
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
                        fabric.worker.transaction.TransactionManager $tm571 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled574 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff572 = 1;
                        boolean $doBackoff573 = true;
                        boolean $retry567 = true;
                        boolean $keepReads568 = false;
                        $label565: for (boolean $commit566 = false; !$commit566;
                                        ) {
                            if ($backoffEnabled574) {
                                if ($doBackoff573) {
                                    if ($backoff572 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff572));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e569) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff572 < 5000) $backoff572 *= 2;
                                }
                                $doBackoff573 = $backoff572 <= 32 ||
                                                  !$doBackoff573;
                            }
                            $commit566 = true;
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
                                         RetryException $e569) {
                                    throw $e569;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e569) {
                                    throw $e569;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e569) {
                                    throw $e569;
                                }
                                catch (final Throwable $e569) {
                                    $tm571.getCurrentLog().checkRetrySignal();
                                    throw $e569;
                                }
                            }
                            catch (final fabric.worker.RetryException $e569) {
                                $commit566 = false;
                                continue $label565;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e569) {
                                $commit566 = false;
                                $retry567 = false;
                                $keepReads568 = $e569.keepReads;
                                if ($tm571.checkForStaleObjects()) {
                                    $retry567 = true;
                                    $keepReads568 = false;
                                    continue $label565;
                                }
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid ==
                                      null ||
                                      !$e569.tid.isDescendantOf(
                                                   $currentTid570)) {
                                    throw $e569;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e569);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e569) {
                                $commit566 = false;
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid.isDescendantOf($currentTid570))
                                    continue $label565;
                                if ($currentTid570.parent != null) {
                                    $retry567 = false;
                                    throw $e569;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e569) {
                                $commit566 = false;
                                if ($tm571.checkForStaleObjects())
                                    continue $label565;
                                $retry567 = false;
                                throw new fabric.worker.AbortException($e569);
                            }
                            finally {
                                if ($commit566) {
                                    fabric.common.TransactionID $currentTid570 =
                                      $tm571.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e569) {
                                        $commit566 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e569) {
                                        $commit566 = false;
                                        $retry567 = false;
                                        $keepReads568 = $e569.keepReads;
                                        if ($tm571.checkForStaleObjects()) {
                                            $retry567 = true;
                                            $keepReads568 = false;
                                            continue $label565;
                                        }
                                        if ($e569.tid ==
                                              null ||
                                              !$e569.tid.isDescendantOf(
                                                           $currentTid570))
                                            throw $e569;
                                        throw new fabric.worker.
                                                UserAbortException($e569);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e569) {
                                        $commit566 = false;
                                        $currentTid570 = $tm571.getCurrentTid();
                                        if ($currentTid570 != null) {
                                            if ($e569.tid.equals(
                                                            $currentTid570) ||
                                                  !$e569.tid.
                                                  isDescendantOf(
                                                    $currentTid570)) {
                                                throw $e569;
                                            }
                                        }
                                    }
                                } else if ($keepReads568) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit566) {
                                    {  }
                                    if ($retry567) { continue $label565; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -105, -99, 96, -23,
    102, 26, 43, -70, -106, 122, 95, -12, 2, 113, 20, 122, 73, 105, -3, -103,
    86, 32, 36, -117, -83, -108, -25, -27, 17, -116, -80, 50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
