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
        
        public static final byte[] $classHash = new byte[] { -16, -60, 10, -3,
        -13, -76, -45, -98, -31, 8, -128, -60, -61, -122, 19, 90, 82, -36, 24,
        -72, 121, 126, 16, -15, 69, -126, 72, 78, -74, 4, -35, 83 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556640403000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAAK1Ye2wcRxmfO9vnZ+NH4jwcx7GdIzR2ctekEmpwQY2vdnPtJTG2E6ij5pjbm7O33tvd7s7Z54BLEokmKiKqwE1TtbUQckVp3UYgWipVloII0KoIiYd4SBTCH1VLQxBR1EIRJXzfzN577+JKRPJ8k5nvNd/jN7O3fJXU2BbpTdCYqgX4nMnswDCNhSMj1LJZPKRR2x6H1ajSWB0+9+534l1e4o2QJoXqhq4qVIvqNidrIg/SGRrUGQ8eHg0PHCX1Cgrup/YUJ96jg2mLdJuGNjepGdwxUqL/8f7gwhPHWr5fRZonSLOqj3HKVSVk6Jyl+QRpSrJkjFn2vnicxSdIq85YfIxZKtXU48Bo6BOkzVYndcpTFrNHmW1oM8jYZqdMZgmbmUV03wC3rZTCDQvcb5Hup7iqBSOqzQcixJdQmRa3HyIPk+oIqUlodBIY10cypwgKjcFhXAf2BhXctBJUYRmR6mlVj3OytVgie2L/fcAAorVJxqeMrKlqncICaZMuaVSfDI5xS9UngbXGSIEVTjrKKgWmOpMq03SSRTnZWMw3IreAq16EBUU4aS9mE5ogZx1FOcvL1tWDd579kr5f9xIP+Bxniob+14FQV5HQKEswi+kKk4JNfZFzdP3KGS8hwNxexCx5fvjla3ft7Lr4uuTZ7MJzKPYgU3hUWYqt+WVnaMfeKnSjzjRsFUuh4OQiqyPOzkDahGpfn9WIm4HM5sXRn95/4nl2xUsawsSnGFoqCVXVqhhJU9WYdQ/TmUU5i4dJPdPjIbEfJrUwj6g6k6uHEgmb8TCp1sSSzxD/hxAlQAWGqBbmqp4wMnOT8ikxT5uEkAb4I1WEeKYJ2dMDtI2QT77DSSQ4ZSRZMKal2CyUdxD+GLWUqSD0raUquxTDnAvalhK0UjpXgVOuy8Pvi0GtU4VjaQfAD/P/rC+N/rfMejwQ2q2KEWcxakOenJoZHNGgLfYbWpxZUUU7uxIma1eeFHVTj7VuQ72KyHgg153FKJEvu5AaHLr2UvRNWXMo6wSOkz7pn8xnvn/+fYBTc0kjZYc5Zg/63SJN2FsBQKsAoNWyJx0ILYZfECXks0WvZfU2gd5PmxrlCcNKponHIw65TsgLW5D5aUAUsNS0Y+yBe794pheylzZnqyGPyOovbqEc8IRhRqEvokrz6Xc/uHBu3sg1Eyf+kh4vlcQe7S2OmGUoLA4YmFPf101fjq7M+72IL/UAfZxCcQKOdBXbKOjVgQzuYTRqIqQRY0A13MqAVQOfsozZ3IqohDU4tMmiwGAVOSgg8zNj5jO//8VfbxeXSQZdm/NgeIzxgbyORmXNondbc7EftxgDvrfOj3zz8aunj4rAA8c2N4N+HEPQyVQUwVdff+gPf/7T0m+8uWRxUmta6gw0eFocpvUG/PPA33/xD/sSF5ACOoccTOjOgoKJprfnnAN40ACiwHfbf1hPGnE1odKYxrBU/tP8id0v/+1si8y3BisyehbZeXMFufVNg+TEm8f+2SXUeBS8nnIBzLFJzFub07zPsugc+pE++astT/6MPgOlD4hlq8eZACGPU73oVDtgabnewv0Okec9gneXGHdjiIQGIvY+hUOvjGmnWK+1S2+JYbxucyU7EVx+uiP02SsSJrIlizp6XGDiCM3rpj3PJ9/39vp+4iW1E6RF3PRU50coYB1UywTc1XbIWYyQWwr2C+9deckMZFuys7hd8swWN0sOnmCO3DhvkP0hyyuD9hsg1O2E3BpwaA/urjVxXJf2EDG5U4hsE+N2HHZkarZeTSZTHOtC6O7npE7VVT4GuXSJ8oilJqGfZpy7mJ1ZePRG4OyCrEP5YNlW8mbIl5GPFmHqFmEvDVZ6KlkREsPvXJh/7bn50/JCbyu8fof0VPLF337088D5y2+4QHsVPK0kluB4R2HwNkMQ1kPQ7nXoXpfghcsED6d3ZaLWhFE7YMCtnpLm7naz2YQ214EsJO3WqEM/72LzYAWbfemsPtEljY6eIw49lKcPjg8QWNkfKBrPRpBLOVRz8Wdc+oPD50qto9S0Q+MF1gUo3Nz8JhD8mkNPuZi/v6J5lDrp0OOF5uHBeZNsgJCnAwSfdeh5F/PHKppHqScc+liB+bpp3ZjVoSrKutCCSjyg7D1CnuMwhzdbzQMuLijuBeEVBcEBO1SdaplirIZ3vh/ntwub6cqy8GbBLyUpnAezWQBflw/g2WcQbm4qfuFgO28p9+oXrbx0amExfujZ3V4H3ofBAefTLGfah6hQ8kl5QHzo5ID68pUte0PTb09KVNhaZLaY+7sHlt+4Z7vyDS+pyiJyyddVodBAIQ43WAw+DvXxAjTuLiyIEMStEwrhPYe+kJ/LXAWUJIPkhd/9HpyvsPcVHNKc9MtM+TFT/srPWH/OmVRhRyAmBgnpr5G074MyRyjTDijyvkP/nidawf1HKuydweEk9JIyxZTpA86H7qBzeSAZgoKfMdR40VkaUQWkwHMHITtbHUpWmQ64GX1mKqZBWxSesEEq6r/h0A9Xd8KFCnvncPg6PB+nqH2QpbnbAWtjhqExqrudEVDXM0TIrnmHmhXy9VjpaVDEcKi6utN8q8Let3F4ClKiZ44CCNLmIAg+iwLyWeSOH+XO9wVCbgs6tPfjnQ9Fehy6eXXne7HC3gUcAKp9FksaMwIIHnXzeiuYjBGye42kt/3743mNIh869PrqvH6lwt6rOHyPk0Y/vlMiNMY0wbcEoNFagg3i6gAI3uzyMe78OKSELrGlt+/b2V7mQ3xjyc91jtxLi811GxYP/058TGZ/+KmHb7VEStPyX715c59psYQqTlIv38CmICtworzLCaoOiTjZa5LjImRKcuD/fiSC2JEdlgRPR8rCHxmXr2/4l69u/LL4poPAdf/jUsNH13/w68W/1J249ONH1k6M/nHjq3MPt1wbOrX/4CvVb439D2Yw5RH8FAAA";
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
        
        public static final byte[] $classHash = new byte[] { -24, -74, -84, -86,
        8, -92, 113, 73, -57, 56, 34, 114, -26, 102, -18, 81, 37, -15, 71, 107,
        -110, 5, 72, -59, -61, 21, 3, -6, 73, 117, 2, 9 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556640403000L;
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
            
            public static final byte[] $classHash = new byte[] { -24, -74, -84,
            -86, 8, -92, 113, 73, -57, 56, 34, 114, -26, 102, -18, 81, 37, -15,
            71, 107, -110, 5, 72, -59, -61, 21, 3, -6, 73, 117, 2, 9 };
            public static final java.lang.String jlc$CompilerVersion$fabil =
              "0.3.0";
            public static final long jlc$SourceLastModified$fabil =
              1556640403000L;
            public static final java.lang.String jlc$ClassType$fabil =
              "H4sIAAAAAAAAAK1YfWwcxRV/dz5/xo4dJw7BSRzHPiI5hDtCpBJqikiOfFxzJMZOUOsU3Lm9OXvjvd3N7pxzhhillZADUl2ahhREk0qV29LUELVSaNUqEmqBgNIPtUK0qCrkHyBVmkpRS4voR/re7Nz3+bClWrp565n33vzmzXu/md25a1DrOtCTZHHdCIlJm7uhnSwejQ0wx+WJiMFcdz/2jmhLAtFTV76X6PKDPwbNGjMtU9eYMWK6ApbGDrEJFja5CB8YjPYfhEaNDHczd0yA/+D2jAPdtmVMjhqWUJOU+X/61vDJbzzc9qMaaB2GVt0cEkzoWsQyBc+IYWhO8VScO+62RIInhmGZyXliiDs6M/RHUNEyh6Hd1UdNJtIOdwe5axkTpNjupm3uyDmznQTfQthOWhOWg/DbPPhpoRvhmO6K/hjUJXVuJNzD8BgEYlCbNNgoKq6MZVcRlh7DO6kf1Zt0hOkkmcazJoFx3UwIWFdqkVtxcA8qoGl9iosxKzdVwGTYAe0eJIOZo+Eh4ejmKKrWWmmcRUDnvE5RqcFm2jgb5SMCVpXqDXhDqNUow0ImAjpK1aQn3LPOkj0r2K1re++eedTcbfrBh5gTXDMIfwMadZUYDfIkd7ipcc+weWPsFFt54bgfAJU7SpQ9nR8fvX7vpq6XX/d0VlfQ2Rc/xDUxos3Gl/52TaTvrhqC0WBbrk6pULRyuasDaqQ/Y2O2r8x5pMFQdvDlwdc+f+wsv+qHpijUaZaRTmFWLdOslK0b3NnFTe4wwRNRaORmIiLHo1CPzzHd5F7vvmTS5SIKAUN21VnyfwxREl1QiOrxWTeTVvbZZmJMPmdsAGjHH9QC1JwHeGw5ygYA/UkBsfCYleLhuJHmRzC9w/jjzNHGwli3jq7dpln2ZNh1tLCTNoWOml6/t/htccx1pglK7RDisP/P/jKEv+2Iz4ehXadZCR5nLu6TypntAwaWxW7LSHBnRDNmLkRh+YVnZd40Uq67mK8yMj7c6zWlLFFoezK9fcf1F0cueTlHtipwAm738Hn7WYgvOJSOF8qooD3EqnegmSoshJwVQs6a82VCkTPRH8hEqnNlxeW8N6P3T9sGE0nLSWXA55NLXSHt5Yy4/+PIK+i/uW/ooc9+8XhPDaaufSSAu0mqwdJCytNPFJ8YVseI1jp95R/nTk1Z+ZISECyr9HJLqtSe0rg5lsYTyIR59xu72fmRC1NBP7FMIxKgYJiiyCZdpXMUVWx/lv0oGrUxWEIxYAYNZSmrSYw51pF8j8yHpdS0e6lBwSoBKInzM0P26T/8+s9b5JGS5djWAjIe4qK/oK7JWaus4GX52O93OEe9Pz0z8PWnr00flIFHjd5KEwapjWA9M5kEj79++O1335l905/fLAH1tqNPYJln5GKW3cA/H/7+Sz+qTuogiRwdUczQnaMGm6bekAeHJGEgUSF2N3jATFkJPamzuMEpVf7desvm83+ZafP228AeL3oObPpkB/n+m7fDsUsP/7NLuvFpdEjlA5hX85hved7zNsdhk4Qj86XfrX32IjuNqY+85eqPcElFPpW9BKpDQPcnVRjpdcr9vkPa3CbbzRQq6Qnk2Keo6fFiu0b2B9zyM2MnHb751B0Oz32zM3LPVY80cqlLPtZXII0HWUFV3XE29aG/p+5VP9QPQ5s895kpHmTIfJg1w3hyuxHVGYOWovHiU9g7cvpzpbmmtGwKpi0tmjxZ4TNp03OTVydemmEgVlCQupDzm5HzP1TyKo0ut6ldkfGBfLhbmvTKdgM1fTKQfnrcKKBRT6XSgpJETnCrgFq8aWA+l8d5wNFTWFkT6mzmx08+eSM0c9LLSO8C01t2hyi08S4xcp4WORnNsr7aLNJi5wfnpn72/NS0d8C3Fx/HO8x06oW3/vPL0DOX36hA9TV41fJYhdqtxeHrxbAtBTjUqeSSCuGLVgsfNfdm4+bTs9m/qjD7i84RUri59IiohG8p4VuNuNoQ151KbqyAb7AyPp/El8n5qyN/LcpPn5I9Bf5EMXHeVwkU/QApM3ARYPYgBgEpbuyVCqA+Vz3napO6yYxs3AJ4Tw3S8xY5Z6aybY2yxdOWbvr03xc8DwXsIFe9l7Jq7XyXUZlRs18+eSax7zub/YpndqJf9caQd7aEkrPsTed+ef/OM8blq2vvioy/N+ol57qSaUu1v3//3Bu7Nmgn/FCTo4ayS3+xUX8xITQ5HN9ZzP1FtNBdnDeDGK4O3N/zSh4u3KL8xvZSc6A8RcjEVlIvMC0hal8+5PdJr2YVJpcedAF9XmkEqTSClQ6GbKkE8ziTOYgtWdILAhhRJbfOs7qyksBNttNxQ9dKyqJZObpTyc3zr7lwSZNVxh6lBm9L9WPM3YtvpFJpu+I8EjtwLG5ZBmdmpTX2IJTbAVLHlByvsoPp8tWQySElEwtbzeNVxqapOSZgCa5mwOETuuXNOVUJOpHpPQBWgyfNfy0OOpl8rOTfFwZ9psrYU9Q8gSRjZncB+bld8TOd1CHvpK7MzPOl3z4A+yMlP1jc+sjkfSUvL2x9z1UZO03NKSLvgn05UQn3Gpz0IQCHKTm4ONxk8oCSexaGe7bK2Hep+RZeP2hfonThkDxSCXg3zjoG4E4rKRYHnExcJVMLA/5ClbFz1DwvoCUb8OrgqRqOAKSfU3JmceDJ5CtKTi8M/EtVxn5CzQ+RBx2esiZ4JVYKTFh6otJK1iGMrwFMflvJpxa3EjL5qpJPzL+SgkPlhPT68yrLkVePC3jJc7lMip/Oh3sW4OhxJScWh5tM0kpai8B9qQruX1HzGuJmicS8uNfjpHMAU51K1i0ON5nUevLojYVlzptVxt6i5jd4BAR1UxcxFueGRzUZfAcvObqpfwtenlZX+LqjvjZqkVf47Ht7NnXM82VnVdn3X2X34pnWhpvOHPi9/C6R+5LYiK/9ybRhFL44FTzXYa0mdbmORu81ypbij7iegqs6Jj8Jua63PY13sFY8DfrvXRnCzlzj7XRn2qGv1nN/u+mjuob9l+XnAaKtKy/NnW2YPRy9uLXHeT/51wduub5r/ETt7ld/0VHzcTTtb/wfvHnNvk0XAAA=";
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
        
        public static final byte[] $classHash = new byte[] { 40, -68, 105, 100,
        -41, -29, -15, 57, 14, -37, -33, 115, 122, -127, 17, -82, 96, -122, -82,
        1, -79, -29, 120, -48, -88, -7, 103, -8, -10, -89, 8, 86 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556640403000L;
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
        
        public static final byte[] $classHash = new byte[] { 7, 87, -34, 126,
        -12, 25, 35, -106, -127, -64, 114, -53, -10, 50, -34, -67, -110, 123,
        43, -33, 96, 92, 47, -29, 32, -17, 101, 64, -109, -94, -60, -39 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556640403000L;
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
                        fabric.worker.transaction.TransactionManager $tm498 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled501 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff499 = 1;
                        boolean $doBackoff500 = true;
                        boolean $retry494 = true;
                        boolean $keepReads495 = false;
                        $label492: for (boolean $commit493 = false; !$commit493;
                                        ) {
                            if ($backoffEnabled501) {
                                if ($doBackoff500) {
                                    if ($backoff499 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff499));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e496) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff499 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff499 =
                                          java.lang.Math.
                                            min(
                                              $backoff499 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff500 = $backoff499 <= 32 ||
                                                  !$doBackoff500;
                            }
                            $commit493 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.AbstractList._Static._Proxy.
                                  $instance.
                                  set$LOCAL_STORE(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                            }
                            catch (final fabric.worker.RetryException $e496) {
                                $commit493 = false;
                                continue $label492;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e496) {
                                $commit493 = false;
                                $retry494 = false;
                                $keepReads495 = $e496.keepReads;
                                fabric.common.TransactionID $currentTid497 =
                                  $tm498.getCurrentTid();
                                if ($e496.tid ==
                                      null ||
                                      !$e496.tid.isDescendantOf(
                                                   $currentTid497)) {
                                    throw $e496;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e496);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e496) {
                                $commit493 = false;
                                fabric.common.TransactionID $currentTid497 =
                                  $tm498.getCurrentTid();
                                if ($e496.tid.isDescendantOf($currentTid497))
                                    continue $label492;
                                if ($currentTid497.parent != null) {
                                    $retry494 = false;
                                    throw $e496;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e496) {
                                $commit493 = false;
                                $retry494 = false;
                                if ($tm498.inNestedTxn()) {
                                    $keepReads495 = true;
                                }
                                throw $e496;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid497 =
                                  $tm498.getCurrentTid();
                                if ($commit493) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e496) {
                                        $commit493 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e496) {
                                        $commit493 = false;
                                        $retry494 = false;
                                        $keepReads495 = $e496.keepReads;
                                        if ($e496.tid ==
                                              null ||
                                              !$e496.tid.isDescendantOf(
                                                           $currentTid497))
                                            throw $e496;
                                        throw new fabric.worker.
                                                UserAbortException($e496);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e496) {
                                        $commit493 = false;
                                        $currentTid497 = $tm498.getCurrentTid();
                                        if ($currentTid497 != null) {
                                            if ($e496.tid.equals(
                                                            $currentTid497) ||
                                                  !$e496.tid.
                                                  isDescendantOf(
                                                    $currentTid497)) {
                                                throw $e496;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm498.inNestedTxn() &&
                                          $tm498.checkForStaleObjects()) {
                                        $retry494 = true;
                                        $keepReads495 = false;
                                    }
                                    if ($keepReads495) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e496) {
                                            $currentTid497 = $tm498.getCurrentTid();
                                            if ($currentTid497 != null &&
                                                  ($e496.tid.equals($currentTid497) || !$e496.tid.isDescendantOf($currentTid497))) {
                                                throw $e496;
                                            } else {
                                                $retry494 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit493) {
                                    {  }
                                    if ($retry494) { continue $label492; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 48, -96, 57, -87, 62,
    19, -127, 96, 5, -65, -48, 111, 57, -124, 10, 18, -46, 86, -101, 62, -98,
    -24, -6, 84, 54, -125, 124, 117, 5, -19, -14, -21 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
