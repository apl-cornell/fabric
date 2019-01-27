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
        
        public static final byte[] $classHash = new byte[] { -25, -89, 20, -30,
        11, 39, -15, -60, 24, 119, -96, 25, -80, -126, -48, -29, 38, -58, -40,
        91, 43, 90, 9, 21, -59, 91, 100, 96, 110, -120, 85, -21 };
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
        
        public static final byte[] $classHash = new byte[] { 1, 2, 26, -76, 110,
        98, -116, -6, -115, 44, -30, 23, 125, 83, 74, 56, -82, 51, 48, -94, -68,
        -10, 109, -122, 92, -65, 55, 54, 59, -56, 93, 24 };
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
            
            public static final byte[] $classHash = new byte[] { 1, 2, 26, -76,
            110, 98, -116, -6, -115, 44, -30, 23, 125, 83, 74, 56, -82, 51, 48,
            -94, -68, -10, 109, -122, 92, -65, 55, 54, 59, -56, 93, 24 };
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
        
        public static final byte[] $classHash = new byte[] { 11, 32, 88, -71,
        -29, -126, 58, 82, 28, 86, -58, -6, -96, -12, -113, 106, -49, -46, 81,
        29, 67, 45, 120, 99, 4, 21, -49, 1, 93, 82, 9, 79 };
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
        
        public static final byte[] $classHash = new byte[] { -12, 59, 114, -63,
        -91, -47, 55, 48, 118, -6, -113, 21, 40, -61, -105, -45, 46, 87, -31,
        33, 70, 69, -25, -127, 110, -30, 4, 52, -89, -81, -25, 113 };
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
                        fabric.worker.transaction.TransactionManager $tm561 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled564 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff562 = 1;
                        boolean $doBackoff563 = true;
                        boolean $retry557 = true;
                        boolean $keepReads558 = false;
                        $label555: for (boolean $commit556 = false; !$commit556;
                                        ) {
                            if ($backoffEnabled564) {
                                if ($doBackoff563) {
                                    if ($backoff562 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff562));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e559) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff562 < 5000) $backoff562 *= 2;
                                }
                                $doBackoff563 = $backoff562 <= 32 ||
                                                  !$doBackoff563;
                            }
                            $commit556 = true;
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
                                         RetryException $e559) {
                                    throw $e559;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e559) {
                                    throw $e559;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e559) {
                                    throw $e559;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e559) {
                                    throw $e559;
                                }
                                catch (final Throwable $e559) {
                                    $tm561.getCurrentLog().checkRetrySignal();
                                    throw $e559;
                                }
                            }
                            catch (final fabric.worker.RetryException $e559) {
                                $commit556 = false;
                                continue $label555;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e559) {
                                $commit556 = false;
                                $retry557 = false;
                                $keepReads558 = $e559.keepReads;
                                if ($tm561.checkForStaleObjects()) {
                                    $retry557 = true;
                                    $keepReads558 = false;
                                    continue $label555;
                                }
                                fabric.common.TransactionID $currentTid560 =
                                  $tm561.getCurrentTid();
                                if ($e559.tid ==
                                      null ||
                                      !$e559.tid.isDescendantOf(
                                                   $currentTid560)) {
                                    throw $e559;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e559);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e559) {
                                $commit556 = false;
                                fabric.common.TransactionID $currentTid560 =
                                  $tm561.getCurrentTid();
                                if ($e559.tid.isDescendantOf($currentTid560))
                                    continue $label555;
                                if ($currentTid560.parent != null) {
                                    $retry557 = false;
                                    throw $e559;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e559) {
                                $commit556 = false;
                                if ($tm561.checkForStaleObjects())
                                    continue $label555;
                                fabric.common.TransactionID $currentTid560 =
                                  $tm561.getCurrentTid();
                                if ($e559.tid.isDescendantOf($currentTid560)) {
                                    $retry557 = true;
                                }
                                else if ($currentTid560.parent != null) {
                                    $retry557 = false;
                                    throw $e559;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e559) {
                                $commit556 = false;
                                if ($tm561.checkForStaleObjects())
                                    continue $label555;
                                $retry557 = false;
                                throw new fabric.worker.AbortException($e559);
                            }
                            finally {
                                if ($commit556) {
                                    fabric.common.TransactionID $currentTid560 =
                                      $tm561.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e559) {
                                        $commit556 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e559) {
                                        $commit556 = false;
                                        $retry557 = false;
                                        $keepReads558 = $e559.keepReads;
                                        if ($tm561.checkForStaleObjects()) {
                                            $retry557 = true;
                                            $keepReads558 = false;
                                            continue $label555;
                                        }
                                        if ($e559.tid ==
                                              null ||
                                              !$e559.tid.isDescendantOf(
                                                           $currentTid560))
                                            throw $e559;
                                        throw new fabric.worker.
                                                UserAbortException($e559);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e559) {
                                        $commit556 = false;
                                        $currentTid560 = $tm561.getCurrentTid();
                                        if ($currentTid560 != null) {
                                            if ($e559.tid.equals(
                                                            $currentTid560) ||
                                                  !$e559.tid.
                                                  isDescendantOf(
                                                    $currentTid560)) {
                                                throw $e559;
                                            }
                                        }
                                    }
                                } else if ($keepReads558) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit556) {
                                    {  }
                                    if ($retry557) { continue $label555; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 103, 71, 40, -117, -27,
    124, -69, -8, 7, 79, -3, -29, -109, 26, 98, -43, -121, 85, 81, -11, 117,
    -102, -32, 67, 116, -43, 93, 125, 86, 95, 25, 55 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
