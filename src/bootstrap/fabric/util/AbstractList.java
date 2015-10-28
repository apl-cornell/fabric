package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
  extends fabric.util.List, fabric.util.AbstractCollection
{
    
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
     * Test whether this list is equal to another object. A List is defined to
     be
     * equal to an object if and only if that object is also a List, and the two
     * lists have the same sequence. Two lists l1 and l2 are equal if and only
     * if <code>l1.size() == l2.size()</code>, and for every integer n between 0
     * and <code>l1.size() - 1</code> inclusive, <code>l1.get(n) == null ?
     * l2.get(n) == null : l1.get(n).equals(l2.get(n))</code>.
     * <p>
     *
     * This implementation returns true if the object is this, or false if the
     * object is not a List.  Otherwise, it iterates over both lists (with
     * iterator(Store)), returning false if two elements compare false or one
     list
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
     * This ensures that the general contract of Object.hashCode() is adhered
     to.
     *
     * @return the hash code of this list
     *
     * @see Object#hashCode()
     * @see #equals(Object)
     */
    public int hashCode();
    
    /**
     * Obtain the first index at which a given object is to be found in this
     * list. This implementation follows a listIterator(store) until a match is
     found,
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
      extends fabric.util.Iterator, fabric.lang.Object
    {
        
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
         * Removes the last object retrieved by <code>next()</code> from the
         list,
         * if the list supports object removal.
         * 
         * @throws ConcurrentModificationException
         *           if the list has been modified elsewhere.
         * @throws IllegalStateException
         *           if the iterator is positioned before the start of the list
         or
         *           the last object has already been removed.
         * @throws UnsupportedOperationException
         *           if the list does not support removing elements.
         */
        public void remove();
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements AnonymousIterator
        {
            
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
            
            public native fabric.util.AbstractList.AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$();
            
            public native boolean hasNext();
            
            public native fabric.lang.Object next();
            
            public native void remove();
            
            public _Proxy(AnonymousIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements AnonymousIterator
        {
            
            public fabric.util.AbstractList get$out$() { return this.out$; }
            
            private fabric.util.AbstractList out$;
            
            public int get$initSize() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.initSize;
            }
            
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
            
            public int get$initModCount() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.initModCount;
            }
            
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
            
            public native AnonymousIterator
              fabric$util$AbstractList$AnonymousIterator$();
            
            /**
             * Checks for modifications made to the list from elsewhere while
             iteration
             * is in progress.
             * 
             * @throws ConcurrentModificationException
             *           if the list has been modified elsewhere.
             */
            private native void checkMod();
            
            /**
             * Tests to see if there are any more objects to return.
             * 
             * @return True if the end of the list has not yet been reached.
             */
            public native boolean hasNext();
            
            /**
             * Retrieves the next object from the list.
             * 
             * @return The next object.
             * @throws NoSuchElementException
             *           if there are no more objects to retrieve.
             * @throws ConcurrentModificationException
             *           if the list has been modified elsewhere.
             */
            public native fabric.lang.Object next();
            
            /**
             * Removes the last object retrieved by <code>next()</code> from the
             list,
             * if the list supports object removal.
             * 
             * @throws ConcurrentModificationException
             *           if the list has been modified elsewhere.
             * @throws IllegalStateException
             *           if the iterator is positioned before the start of the
             list or
             *           the last object has already been removed.
             * @throws UnsupportedOperationException
             *           if the list does not support removing elements.
             */
            public native void remove();
            
            public native fabric.lang.Object $initLabels();
            
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
                this.out$ = (fabric.util.AbstractList)
                              $readRef(fabric.util.AbstractList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
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
              implements fabric.util.AbstractList.AnonymousIterator._Static
            {
                
                public _Proxy(fabric.util.AbstractList.AnonymousIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.AnonymousIterator.
                  _Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      AnonymousIterator.
                      _Static.
                      _Impl impl =
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
              implements fabric.util.AbstractList.AnonymousIterator._Static
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
                    return new fabric.util.AbstractList.AnonymousIterator.
                      _Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
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
    public fabric.util.ListIterator listIterator(fabric.worker.Store store,
                                                 final int index);
    
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
     * This implementation first checks for illegal or out of range arguments.
     It
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
     * any way other than through the returned list, the result of any
     subsequent
     * operations on the returned list is undefined.
     * <p>
     *
     * This implementation returns a subclass of AbstractList. It stores, in
     * private fields, the offset and size of the sublist, and the expected
     * modCount of the backing list. If the backing list implements
     RandomAccess,
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
     * The <code>listIterator(Store, int)</code> method returns a "wrapper
     object"
     * over a list iterator on the backing list, which is created with the
     * corresponding method on the backing list. The
     <code>iterator(Store)</code>
     * method merely returns listIterator(store), and the <code>size()</code>
     method
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
      extends fabric.util.ListIterator, fabric.lang.Object
    {
        
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
          implements AbstractListIterator
        {
            
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
            
            public native boolean hasNext();
            
            public native boolean hasPrevious();
            
            public native fabric.lang.Object next();
            
            public native fabric.lang.Object previous();
            
            public native int nextIndex();
            
            public native int previousIndex();
            
            public native void remove();
            
            public native void set(fabric.lang.Object arg1);
            
            public native void add(fabric.lang.Object arg1);
            
            public _Proxy(AbstractListIterator._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl extends fabric.lang.Object._Impl
          implements AbstractListIterator
        {
            
            public fabric.util.AbstractList get$out$() { return this.out$; }
            
            private fabric.util.AbstractList out$;
            
            public int get$index() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.index;
            }
            
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
            
            /**
             * 
             */
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
            
            private int lastReturned;
            
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
            private native AbstractListIterator
              fabric$util$AbstractList$AbstractListIterator$(int index);
            
            /**
             * Checks for modifications made to the list from
             * elsewhere while iteration is in progress.
             *
             * @throws ConcurrentModificationException if the
             *         list has been modified elsewhere.
             */
            private native void checkMod();
            
            /**
             * Tests to see if there are any more objects to
             * return.
             *
             * @return True if the end of the list has not yet been
             *         reached.
             */
            public native boolean hasNext();
            
            /**
             * Tests to see if there are objects prior to the
             * current position in the list.
             *
             * @return True if objects exist prior to the current
             *         position of the iterator.
             */
            public native boolean hasPrevious();
            
            /**
             * Retrieves the next object from the list.
             *
             * @return The next object.
             * @throws NoSuchElementException if there are no
             *         more objects to retrieve.
             * @throws ConcurrentModificationException if the
             *         list has been modified elsewhere.
             */
            public native fabric.lang.Object next();
            
            /**
             * Retrieves the previous object from the list.
             *
             * @return The next object.
             * @throws NoSuchElementException if there are no
             *         previous objects to retrieve.
             * @throws ConcurrentModificationException if the
             *         list has been modified elsewhere.
             */
            public native fabric.lang.Object previous();
            
            /**
             * Returns the index of the next element in the
             * list, which will be retrieved by <code>next()</code>
             *
             * @return The index of the next element.
             */
            public native int nextIndex();
            
            /**
             * Returns the index of the previous element in the
             * list, which will be retrieved by <code>previous()</code>
             *
             * @return The index of the previous element.
             */
            public native int previousIndex();
            
            /**
             * Removes the last object retrieved by <code>next()</code>
             * or <code>previous()</code> from the list, if the list
             * supports object removal.
             *
             * @throws IllegalStateException if the iterator is positioned
             *         before the start of the list or the last object has
             already
             *         been removed.
             * @throws UnsupportedOperationException if the list does
             *         not support removing elements.
             * @throws ConcurrentModificationException if the list
             *         has been modified elsewhere.
             */
            public native void remove();
            
            /**
             * Replaces the last object retrieved by <code>next()</code>
             * or <code>previous</code> with o, if the list supports object
             * replacement and an add or remove operation has not already
             * been performed.
             *
             * @throws IllegalStateException if the iterator is positioned
             *         before the start of the list or the last object has
             already
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
            public native void set(fabric.lang.Object o);
            
            /**
             * Adds the supplied object before the element that would be
             returned
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
            public native void add(fabric.lang.Object o);
            
            public native fabric.lang.Object $initLabels();
            
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
                this.out$ = (fabric.util.AbstractList)
                              $readRef(fabric.util.AbstractList._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(),
                                       in,
                                       store,
                                       intraStoreRefs,
                                       interStoreRefs);
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
              implements fabric.util.AbstractList.AbstractListIterator._Static
            {
                
                public _Proxy(fabric.util.AbstractList.AbstractListIterator.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.
                  AbstractListIterator._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      AbstractListIterator.
                      _Static.
                      _Impl impl =
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
              implements fabric.util.AbstractList.AbstractListIterator._Static
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
                    return new fabric.util.AbstractList.AbstractListIterator.
                      _Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
     * This class follows the implementation requirements set forth in
     * {@link AbstractList#subList(int, int)}. It matches Sun's implementation
     * by using a non-public top-level class in the same package.
     *
     * @author Original author unknown
     * @author Eric Blake (ebb9@email.byu.edu)
     */
    public static interface SubList
      extends fabric.util.AbstractList
    {
        
        public SubList fabric$util$AbstractList$SubList$();
        
        /**
         * 
         */
        public static interface SubListIterator
          extends fabric.util.ListIterator, fabric.lang.Object
        {
            
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
             *         before the start of the list or the last object has
             already
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
             *         before the start of the list or the last object has
             already
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
             * Adds the supplied object before the element that would be
             returned
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
              implements SubListIterator
            {
                
                public fabric.util.AbstractList.SubList get$out$() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      get$out$();
                }
                
                public int get$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      get$index();
                }
                
                public int set$index(int val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      set$index(val);
                }
                
                public int postInc$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      postInc$index();
                }
                
                public int postDec$index() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      postDec$index();
                }
                
                public fabric.util.ListIterator get$i() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      get$i();
                }
                
                public fabric.util.ListIterator set$i(
                  fabric.util.ListIterator val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      set$i(val);
                }
                
                public int get$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      get$position();
                }
                
                public int set$position(int val) {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      set$position(val);
                }
                
                public int postInc$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      postInc$position();
                }
                
                public int postDec$position() {
                    return ((fabric.util.AbstractList.SubList.SubListIterator.
                              _Impl)
                              fetch()).
                      postDec$position();
                }
                
                public native boolean hasNext();
                
                public native boolean hasPrevious();
                
                public native fabric.lang.Object next();
                
                public native fabric.lang.Object previous();
                
                public native int nextIndex();
                
                public native int previousIndex();
                
                public native void remove();
                
                public native void set(fabric.lang.Object arg1);
                
                public native void add(fabric.lang.Object arg1);
                
                public _Proxy(SubListIterator._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
            }
            
            public static final class _Impl extends fabric.lang.Object._Impl
              implements SubListIterator
            {
                
                public fabric.util.AbstractList.SubList get$out$() {
                    return this.out$;
                }
                
                private SubList out$;
                
                public int get$index() {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerRead(this);
                    return this.index;
                }
                
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
                
                /**
                 * 
                 */
                private int index;
                
                public fabric.util.ListIterator get$i() {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerRead(this);
                    return this.i;
                }
                
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
                private native SubListIterator
                  fabric$util$AbstractList$SubListIterator$(int index);
                
                /**
                 * Tests to see if there are any more objects to
                 * return.
                 *
                 * @return True if the end of the list has not yet been
                 *         reached.
                 */
                public native boolean hasNext();
                
                /**
                 * Tests to see if there are objects prior to the
                 * current position in the list.
                 *
                 * @return True if objects exist prior to the current
                 *         position of the iterator.
                 */
                public native boolean hasPrevious();
                
                /**
                 * Retrieves the next object from the list.
                 *
                 * @return The next object.
                 * @throws NoSuchElementException if there are no
                 *         more objects to retrieve.
                 * @throws ConcurrentModificationException if the
                 *         list has been modified elsewhere.
                 */
                public native fabric.lang.Object next();
                
                /**
                 * Retrieves the previous object from the list.
                 *
                 * @return The next object.
                 * @throws NoSuchElementException if there are no
                 *         previous objects to retrieve.
                 * @throws ConcurrentModificationException if the
                 *         list has been modified elsewhere.
                 */
                public native fabric.lang.Object previous();
                
                /**
                 * Returns the index of the next element in the
                 * list, which will be retrieved by <code>next()</code>
                 *
                 * @return The index of the next element.
                 */
                public native int nextIndex();
                
                /**
                 * Returns the index of the previous element in the
                 * list, which will be retrieved by <code>previous()</code>
                 *
                 * @return The index of the previous element.
                 */
                public native int previousIndex();
                
                /**
                 * Removes the last object retrieved by <code>next()</code>
                 * from the list, if the list supports object removal.
                 *
                 * @throws IllegalStateException if the iterator is positioned
                 *         before the start of the list or the last object has
                 already
                 *         been removed.
                 * @throws UnsupportedOperationException if the list does
                 *         not support removing elements.
                 */
                public native void remove();
                
                /**
                 * Replaces the last object retrieved by <code>next()</code>
                 * or <code>previous</code> with o, if the list supports object
                 * replacement and an add or remove operation has not already
                 * been performed.
                 *
                 * @throws IllegalStateException if the iterator is positioned
                 *         before the start of the list or the last object has
                 already
                 *         been removed.
                 * @throws UnsupportedOperationException if the list doesn't
                 support
                 *         the addition or removal of elements.
                 * @throws ClassCastException if the type of o is not a valid
                 type
                 *         for this list.
                 * @throws IllegalArgumentException if something else related to
                 o
                 *         prevents its addition.
                 * @throws ConcurrentModificationException if the list
                 *         has been modified elsewhere.
                 */
                public native void set(fabric.lang.Object o);
                
                /**
                 * Adds the supplied object before the element that would be
                 returned
                 * by a call to <code>next()</code>, if the list supports
                 addition.
                 * 
                 * @param o The object to add to the list.
                 * @throws UnsupportedOperationException if the list doesn't
                 support
                 *         the addition of new elements.
                 * @throws ClassCastException if the type of o is not a valid
                 type
                 *         for this list.
                 * @throws IllegalArgumentException if something else related to
                 o
                 *         prevents its addition.
                 * @throws ConcurrentModificationException if the list
                 *         has been modified elsewhere.
                 */
                public native void add(fabric.lang.Object o);
                
                public native fabric.lang.Object $initLabels();
                
                public _Impl(fabric.worker.Store $location,
                             final SubList out$) {
                    super($location);
                    this.out$ = out$;
                }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.util.AbstractList.SubList.SubListIterator.
                      _Proxy(
                      this);
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
                    this.out$ =
                      (fabric.util.AbstractList.SubList)
                        $readRef(fabric.util.AbstractList.SubList._Proxy.class,
                                 (fabric.common.RefTypeEnum) refTypes.next(),
                                 in, store, intraStoreRefs, interStoreRefs);
                    this.index = in.readInt();
                    this.i = (fabric.util.ListIterator)
                               $readRef(fabric.util.ListIterator._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(),
                                        in,
                                        store,
                                        intraStoreRefs,
                                        interStoreRefs);
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
                      SubListIterator._Static
                      $instance;
                    
                    static {
                        fabric.
                          util.
                          AbstractList.
                          SubList.
                          SubListIterator.
                          _Static.
                          _Impl impl =
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
                            _Static)
                            impl.$getProxy();
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
                    
                    public _Impl(fabric.worker.Store store, long onum,
                                 int version, long expiry,
                                 fabric.worker.Store labelStore, long labelOnum,
                                 fabric.worker.Store accessPolicyStore,
                                 long accessPolicyOnum, java.io.ObjectInput in,
                                 java.util.Iterator refTypes,
                                 java.util.Iterator intraStoreRefs,
                                 java.util.Iterator interStoreRefs)
                          throws java.io.IOException,
                        java.lang.ClassNotFoundException {
                        super(store, onum, version, expiry, labelStore,
                              labelOnum, accessPolicyStore, accessPolicyOnum,
                              in, refTypes, intraStoreRefs, interStoreRefs);
                    }
                    
                    public _Impl(fabric.worker.Store store) { super(store); }
                    
                    protected fabric.lang.Object._Proxy $makeProxy() {
                        return new fabric.util.AbstractList.SubList.
                          SubListIterator._Static._Proxy(
                          this);
                    }
                    
                    private void $init() {  }
                }
                
            }
            
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
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         * @throws ClassCastException if o cannot be added to the backing list
         due
         *         to its type
         * @throws IllegalArgumentException if o cannot be added to the backing
         list
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
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public fabric.lang.Object get(int index);
        
        /**
         * Specified by AbstractList.subList to delegate to the backing list.
         *
         * @param index the index to insert at
         * @param o the object to add
         * @throws ConcurrentModificationException if the backing list has been
         *         modified externally to this sublist
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         * @throws UnsupportedOperationException if the backing list does not
         *         support the add operation.
         * @throws ClassCastException if o cannot be added to the backing list
         due
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
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
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
         * @return true if this list was modified, in other words, c is
         non-empty
         * @throws ConcurrentModificationException if the backing list has been
         *         modified externally to this sublist
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         * @throws UnsupportedOperationException if this list does not support
         the
         *         addAll operation
         * @throws ClassCastException if some element of c cannot be added to
         this
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
         * @return true if this list was modified, in other words, c is
         non-empty
         * @throws ConcurrentModificationException if the backing list has been
         *         modified externally to this sublist
         * @throws UnsupportedOperationException if this list does not support
         the
         *         addAll operation
         * @throws ClassCastException if some element of c cannot be added to
         this
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
          implements SubList
        {
            
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
            
            public native fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$();
            
            public native fabric.util.AbstractList.SubList
              fabric$util$AbstractList$SubList$(fabric.util.AbstractList arg1,
                                                int arg2, int arg3);
            
            public native void checkMod();
            
            public _Proxy(SubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.util.AbstractList._Impl
          implements SubList
        {
            
            public native SubList fabric$util$AbstractList$SubList$();
            
            public fabric.util.AbstractList get$backingList() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
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
            
            /** The original list. */
            fabric.util.AbstractList backingList;
            
            public int get$offset() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.offset;
            }
            
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
            
            /** The index of the first element of the sublist. */
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
            public native SubList fabric$util$AbstractList$SubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex);
            
            /**
             * This method checks the two modCount fields to ensure that there
             has
             * not been a concurrent modification, returning if all is okay.
             *
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             */
            public native void checkMod();
            
            /**
             * This method checks that a value is between 0 and size
             (inclusive). If
             * it is not, an exception is thrown.
             *
             * @param index the value to check
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
             size()
             */
            private native void checkBoundsInclusive(int index);
            
            /**
             * This method checks that a value is between 0 (inclusive) and size
             * (exclusive). If it is not, an exception is thrown.
             *
             * @param index the value to check
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
             size()
             */
            private native void checkBoundsExclusive(int index);
            
            /**
             * Specified by AbstractList.subList to return the private field
             size.
             *
             * @return the sublist size
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             */
            public native int size();
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             *
             * @param index the location to modify
             * @param o the new value
             * @return the old value
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws UnsupportedOperationException if the backing list does
             not
             *         support the set operation
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
             size()
             * @throws ClassCastException if o cannot be added to the backing
             list due
             *         to its type
             * @throws IllegalArgumentException if o cannot be added to the
             backing list
             *         for some other reason
             */
            public native fabric.lang.Object set(int index,
                                                 fabric.lang.Object o);
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             *
             * @param index the location to get from
             * @return the object at that location
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
             size()
             */
            public native fabric.lang.Object get(int index);
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             *
             * @param index the index to insert at
             * @param o the object to add
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
             size()
             * @throws UnsupportedOperationException if the backing list does
             not
             *         support the add operation.
             * @throws ClassCastException if o cannot be added to the backing
             list due
             *         to its type.
             * @throws IllegalArgumentException if o cannot be added to the
             backing
             *         list for some other reason.
             */
            public native void add(int index, fabric.lang.Object o);
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             *
             * @param index the index to remove
             * @return the removed object
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
             size()
             * @throws UnsupportedOperationException if the backing list does
             not
             *         support the remove operation
             */
            public native fabric.lang.Object remove(int index);
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             * This does no bounds checking, as it assumes it will only be
             called
             * by trusted code like clear() which has already checked the
             bounds.
             *
             * @param fromIndex the lower bound, inclusive
             * @param toIndex the upper bound, exclusive
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws UnsupportedOperationException if the backing list does
             *         not support removing elements.
             */
            public native void removeRange(int fromIndex, int toIndex);
            
            /**
             * Specified by AbstractList.subList to delegate to the backing
             list.
             *
             * @param index the location to insert at
             * @param c the collection to insert
             * @return true if this list was modified, in other words, c is
             non-empty
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
             size()
             * @throws UnsupportedOperationException if this list does not
             support the
             *         addAll operation
             * @throws ClassCastException if some element of c cannot be added
             to this
             *         list due to its type
             * @throws IllegalArgumentException if some element of c cannot be
             added
             *         to this list for some other reason
             * @throws NullPointerException if the specified collection is null
             */
            public native boolean addAll(int index, fabric.util.Collection c);
            
            /**
             * Specified by AbstractList.subList to return addAll(size, c).
             *
             * @param c the collection to insert
             * @return true if this list was modified, in other words, c is
             non-empty
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws UnsupportedOperationException if this list does not
             support the
             *         addAll operation
             * @throws ClassCastException if some element of c cannot be added
             to this
             *         list due to its type
             * @throws IllegalArgumentException if some element of c cannot be
             added
             *         to this list for some other reason
             * @throws NullPointerException if the specified collection is null
             */
            public native boolean addAll(fabric.util.Collection c);
            
            /**
             * Specified by AbstractList.subList to return listIterator(Store).
             *
             * @return an iterator over the sublist
             */
            public native fabric.util.Iterator iterator(
              fabric.worker.Store store);
            
            /**
             * Specified by AbstractList.subList to return a wrapper around the
             * backing list's iterator.
             *
             * @param index the start location of the iterator
             * @return a list iterator over the sublist
             * @throws ConcurrentModificationException if the backing list has
             been
             *         modified externally to this sublist
             * @throws IndexOutOfBoundsException if the value is out of range
             */
            public native fabric.util.ListIterator listIterator(
              fabric.worker.Store store, final int index);
            
            public native fabric.lang.Object $initLabels();
            
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
              implements fabric.util.AbstractList.SubList._Static
            {
                
                public _Proxy(fabric.util.AbstractList.SubList._Static.
                                _Impl impl) {
                    super(impl);
                }
                
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
                      _Impl impl =
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
              implements fabric.util.AbstractList.SubList._Static
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
                    return new fabric.util.AbstractList.SubList._Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    /**
     * This class is a RandomAccess version of SubList, as required by
     * {@link AbstractList#subList(int, int)}.
     *
     * @author Eric Blake (ebb9@email.byu.edu)
     */
    public static interface RandomAccessSubList
      extends fabric.util.RandomAccess, SubList
    {
        
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
          implements RandomAccessSubList
        {
            
            public native fabric.util.AbstractList.RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList arg1, int arg2, int arg3);
            
            public _Proxy(RandomAccessSubList._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.util.AbstractList.SubList._Impl
          implements RandomAccessSubList
        {
            
            /**
             * Construct the sublist.
             *
             * @param backing the list this comes from
             * @param fromIndex the lower bound, inclusive
             * @param toIndex the upper bound, exclusive
             */
            public native RandomAccessSubList
              fabric$util$AbstractList$RandomAccessSubList$(
              fabric.util.AbstractList backing, int fromIndex, int toIndex);
            
            public native fabric.lang.Object $initLabels();
            
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
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.util.AbstractList.RandomAccessSubList._Static
            {
                
                public _Proxy(fabric.util.AbstractList.RandomAccessSubList.
                                _Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.util.AbstractList.
                  RandomAccessSubList._Static
                  $instance;
                
                static {
                    fabric.
                      util.
                      AbstractList.
                      RandomAccessSubList.
                      _Static.
                      _Impl impl =
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
              implements fabric.util.AbstractList.RandomAccessSubList._Static
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
                    return new fabric.util.AbstractList.RandomAccessSubList.
                      _Static._Proxy(
                      this);
                }
                
                private void $init() {  }
            }
            
        }
        
    }
    
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList
    {
        
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
        
        public native fabric.util.AbstractList fabric$util$AbstractList$();
        
        public fabric.lang.Object get(int arg1) {
            return ((fabric.util.AbstractList) fetch()).get(arg1);
        }
        
        public native void add(int arg1, fabric.lang.Object arg2);
        
        public native boolean addAll(int arg1, fabric.util.Collection arg2);
        
        public native int indexOf(fabric.lang.Object arg1);
        
        public native int lastIndexOf(fabric.lang.Object arg1);
        
        public native fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1);
        
        public native fabric.util.ListIterator listIterator(
          fabric.worker.Store arg1, int arg2);
        
        public native fabric.lang.Object remove(int arg1);
        
        public native void removeRange(int arg1, int arg2);
        
        public native fabric.lang.Object set(int arg1, fabric.lang.Object arg2);
        
        public native fabric.util.List subList(int arg1, int arg2);
        
        public _Proxy(AbstractList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractList
    {
        
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
         * A count of the number of structural modifications that have been made
         to
         * the list (that is, insertions and removals). Structural modifications
         * are ones which change the list size or affect how iterations would
         * behave. This field is available for use by Iterator and ListIterator,
         * in order to throw a {@link ConcurrentModificationException} in
         response
         * to the next operation on the iterator. This <i>fail-fast</i> behavior
         * saves the user from many subtle bugs otherwise possible from
         concurrent
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
        public native fabric.util.AbstractList fabric$util$AbstractList$();
        
        /**
         * Returns the elements at the specified position in the list.
         *
         * @param index the element to return
         * @return the element at that position
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         */
        public abstract fabric.lang.Object get(int index);
        
        /**
         * Insert an element into the list at a given position (optional
         operation).
         * This shifts all existing elements from that position to the end one
         * index to the right.  This version of add has no return, since it is
         * assumed to always succeed if there is no exception. This
         implementation
         * always throws UnsupportedOperationException, and must be overridden
         to
         * make a modifiable List.  If you want fail-fast iterators, be sure to
         * increment modCount when overriding this.
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
         *         some other reason
         * @see #modCount
         */
        public native void add(int index, fabric.lang.Object o);
        
        /**
         * Add an element to the end of the list (optional operation). If the
         list
         * imposes restraints on what can be inserted, such as no null elements,
         * this should be documented. This implementation calls
         * <code>add(size(), o);</code>, and will fail if that version does.
         *
         * @param o the object to add
         * @return true, as defined by Collection for a modified list
         * @throws UnsupportedOperationException if this list does not support
         the
         *         add operation
         * @throws ClassCastException if o cannot be added to this list due to
         its
         *         type
         * @throws IllegalArgumentException if o cannot be added to this list
         for
         *         some other reason
         * @see #add(int, Object)
         */
        public native boolean add(fabric.lang.Object o);
        
        /**
         * Insert the contents of a collection into the list at a given position
         * (optional operation). Shift all elements at that position to the
         right
         * by the number of elements inserted. This operation is undefined if
         * this list is modified during the operation (for example, if you try
         * to insert a list into itself). This implementation uses the iterator
         of
         * the collection, repeatedly calling add(int, Object); this will fail
         * if add does. This can often be made more efficient.
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
         * @see #add(int, Object)
         */
        public native boolean addAll(int index, fabric.util.Collection c);
        
        /**
         * Clear the list, such that a subsequent call to isEmpty() would return
         * true (optional operation). This implementation calls
         * <code>removeRange(0, size())</code>, so it will fail unless remove
         * or removeRange is overridden.
         *
         * @throws UnsupportedOperationException if this list does not support
         the
         *         clear operation
         * @see #remove(int)
         * @see #removeRange(int, int)
         */
        public native void clear();
        
        /**
         * Test whether this list is equal to another object. A List is defined
         to be
         * equal to an object if and only if that object is also a List, and the
         two
         * lists have the same sequence. Two lists l1 and l2 are equal if and
         only
         * if <code>l1.size() == l2.size()</code>, and for every integer n
         between 0
         * and <code>l1.size() - 1</code> inclusive, <code>l1.get(n) == null ?
         * l2.get(n) == null : l1.get(n).equals(l2.get(n))</code>.
         * <p>
         *
         * This implementation returns true if the object is this, or false if
         the
         * object is not a List.  Otherwise, it iterates over both lists (with
         * iterator(Store)), returning false if two elements compare false or
         one list
         * is shorter, and true if the iteration completes successfully.
         *
         * @param o the object to test for equality with this list
         * @return true if o is equal to this list
         * @see Object#equals(Object)
         * @see #hashCode()
         */
        public native boolean equals(fabric.lang.Object o);
        
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
         * This ensures that the general contract of Object.hashCode() is
         adhered to.
         *
         * @return the hash code of this list
         *
         * @see Object#hashCode()
         * @see #equals(Object)
         */
        public native int hashCode();
        
        /**
         * Obtain the first index at which a given object is to be found in this
         * list. This implementation follows a listIterator(store) until a match
         is found,
         * or returns -1 if the list end is reached.
         *
         * @param o the object to search for
         * @return the least integer n such that <code>o == null ? get(n) ==
         null :
         *         o.equals(get(n))</code>, or -1 if there is no such index
         */
        public native int indexOf(fabric.lang.Object o);
        
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
        public native fabric.util.Iterator iterator(fabric.worker.Store store);
        
        /**
         * Obtain the last index at which a given object is to be found in this
         * list. This implementation grabs listIterator(size()), then searches
         * backwards for a match or returns -1.
         *
         * @return the greatest integer n such that <code>o == null ? get(n) ==
         null
         *         : o.equals(get(n))</code>, or -1 if there is no such index
         */
        public native int lastIndexOf(fabric.lang.Object o);
        
        /**
         * Obtain a ListIterator over this list, starting at the beginning. This
         * implementation returns listIterator(0).
         *
         * @return a ListIterator over the elements of this list, in order,
         starting
         *         at the beginning
         */
        public native fabric.util.ListIterator listIterator(
          fabric.worker.Store store);
        
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
         * @param index the position, between 0 and size() inclusive, to begin
         the
         *        iteration from
         * @return a ListIterator over the elements of this list, in order,
         starting
         *         at index
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;
         size()
         * @see #modCount
         */
        public native fabric.util.ListIterator listIterator(
          fabric.worker.Store store, final int index);
        
        /**
         * Remove the element at a given position in this list (optional
         operation).
         * Shifts all remaining elements to the left to fill the gap. This
         * implementation always throws an UnsupportedOperationException.
         * If you want fail-fast iterators, be sure to increment modCount when
         * overriding this.
         *
         * @param index the position within the list of the object to remove
         * @return the object that was removed
         * @throws UnsupportedOperationException if this list does not support
         the
         *         remove operation
         * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;=
         size()
         * @see #modCount
         */
        public native fabric.lang.Object remove(int index);
        
        /**
         * Remove a subsection of the list. This is called by the clear and
         * removeRange methods of the class which implements subList, which are
         * difficult for subclasses to override directly. Therefore, this method
         * should be overridden instead by the more efficient implementation, if
         one
         * exists. Overriding this can reduce quadratic efforts to constant time
         * in some cases!
         * <p>
         *
         * This implementation first checks for illegal or out of range
         arguments. It
         * then obtains a ListIterator over the list using
         listIterator(fromIndex).
         * It then calls next() and remove() on this iterator repeatedly,
         toIndex -
         * fromIndex times.
         *
         * @param fromIndex the index, inclusive, to remove from.
         * @param toIndex the index, exclusive, to remove to.
         * @throws UnsupportedOperationException if the list does
         *         not support removing elements.
         */
        public native void removeRange(int fromIndex, int toIndex);
        
        /**
         * Replace an element of this list with another object (optional
         operation).
         * This implementation always throws an UnsupportedOperationException.
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
         */
        public native fabric.lang.Object set(int index, fabric.lang.Object o);
        
        /**
         * Obtain a List view of a subsection of this list, from fromIndex
         * (inclusive) to toIndex (exclusive). If the two indices are equal, the
         * sublist is empty. The returned list should be modifiable if and only
         * if this list is modifiable. Changes to the returned list should be
         * reflected in this list. If this list is structurally modified in
         * any way other than through the returned list, the result of any
         subsequent
         * operations on the returned list is undefined.
         * <p>
         *
         * This implementation returns a subclass of AbstractList. It stores, in
         * private fields, the offset and size of the sublist, and the expected
         * modCount of the backing list. If the backing list implements
         RandomAccess,
         * the sublist will also.
         * <p>
         *
         * The subclass's <code>set(int, Object)</code>, <code>get(int)</code>,
         * <code>add(int, Object)</code>, <code>remove(int)</code>,
         * <code>addAll(int, Collection)</code> and
         * <code>removeRange(int, int)</code> methods all delegate to the
         * corresponding methods on the backing abstract list, after
         * bounds-checking the index and adjusting for the offset. The
         * <code>addAll(Collection c)</code> method merely returns addAll(size,
         c).
         * The <code>listIterator(Store, int)</code> method returns a "wrapper
         object"
         * over a list iterator on the backing list, which is created with the
         * corresponding method on the backing list. The
         <code>iterator(Store)</code>
         * method merely returns listIterator(store), and the
         <code>size()</code> method
         * merely returns the subclass's size field.
         * <p>
         *
         * All methods first check to see if the actual modCount of the backing
         * list is equal to its expected value, and throw a
         * ConcurrentModificationException if it is not. 
         *
         * @param fromIndex the index that the returned list should start from
         *        (inclusive)
         * @param toIndex the index that the returned list should go to
         (exclusive)
         * @return a List backed by a subsection of this list
         * @throws IndexOutOfBoundsException if fromIndex &lt; 0
         *         || toIndex &gt; size()
         * @throws IllegalArgumentException if fromIndex &gt; toIndex
         * @see ConcurrentModificationException
         * @see RandomAccess
         */
        public native fabric.util.List subList(int fromIndex, int toIndex);
        
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
          implements fabric.util.AbstractList._Static
        {
            
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
                  _Impl impl =
                  (fabric.util.AbstractList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractList._Static._Impl.class);
                $instance = (fabric.util.AbstractList._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractList._Static
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
                this.LOCAL_STORE = (fabric.worker.Store) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.AbstractList._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
