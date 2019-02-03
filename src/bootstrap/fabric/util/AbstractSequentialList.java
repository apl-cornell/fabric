package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Abstract superclass to make it easier to implement the List interface when
 * backed by a sequential-access store, such as a linked list. For random
 * access data, use AbstractList. This class implements the random access
 * methods (<code>get</code>, <code>set</code>, <code>add</code>, and
 * <code>remove</code>) atop the list iterator, opposite of AbstractList's
 * approach of implementing the iterator atop random access.
 * <p>
 *
 * To implement a list, you need an implementation for <code>size()</code>
 * and <code>listIterator</code>.  With just <code>hasNext</code>,
 * <code>next</code>, <code>hasPrevious</code>, <code>previous</code>,
 * <code>nextIndex</code>, and <code>previousIndex</code>, you have an
 * unmodifiable list. For a modifiable one, add <code>set</code>, and for
 * a variable-size list, add <code>add</code> and <code>remove</code>.
 * <p>
 *
 * The programmer should provide a no-argument constructor, and one that
 * accepts another Collection, as recommended by the Collection interface.
 * Unfortunately, there is no way to enforce this in Java.
 *
 * @author Original author unknown
 * @author Bryce McKinlay
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see Collection
 * @see List
 * @see AbstractList
 * @see AbstractCollection
 * @see ListIterator
 * @see LinkedList
 * @since 1.2
 * @status updated to 1.4
 */
public interface AbstractSequentialList extends fabric.util.AbstractList {
    /**
   * The main constructor, for use by subclasses.
   */
    public fabric.util.AbstractSequentialList
      fabric$util$AbstractSequentialList$();
    
    /**
   * Returns a ListIterator over the list, starting from position index.
   * Subclasses must provide an implementation of this method.
   *
   * @param index the starting position of the list
   * @return the list iterator
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
    public abstract fabric.util.ListIterator listIterator(
      fabric.worker.Store store, int index);
    
    /**
   * Insert an element into the list at a given position (optional operation).
   * This shifts all existing elements from that position to the end one
   * index to the right. This version of add has no return, since it is
   * assumed to always succeed if there is no exception. This iteration
   * uses listIterator(index).add(o).
   *
   * @param index the location to insert the item
   * @param o the object to insert
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason.
   * @throws NullPointerException if o is null and the list does not permit
   *         the addition of null values.
   */
    public void add(int index, fabric.lang.Object o);
    
    /**
   * Insert the contents of a collection into the list at a given position
   * (optional operation). Shift all elements at that position to the right
   * by the number of elements inserted. This operation is undefined if
   * this list is modified during the operation (for example, if you try
   * to insert a list into itself).
   * <p>
   *
   * This implementation grabs listIterator(index), then proceeds to use add
   * for each element returned by c's iterator. Sun's online specs are wrong,
   * claiming that this also calls next(): listIterator.add() correctly
   * skips the added element.
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
   * @throws NullPointerException if an object, o, in c is null and the list
   *         does not permit the addition of null values.
   * @see #add(int, Object)
   */
    public boolean addAll(int index, fabric.util.Collection c);
    
    /**
   * Get the element at a given index in this list. This implementation
   * returns listIterator(index).next().
   *
   * @param index the index of the element to be returned
   * @return the element at index index in this list
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
    public fabric.lang.Object get(int index);
    
    /**
   * Obtain an Iterator over this list, whose sequence is the list order. This
   * implementation returns listIterator(store).
   *
   * @return an Iterator over the elements of this list, in order
   */
    public fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Remove the element at a given position in this list (optional operation).
   * Shifts all remaining elements to the left to fill the gap. This
   * implementation uses listIterator(index) and ListIterator.remove().
   *
   * @param index the position within the list of the object to remove
   * @return the object that was removed
   * @throws UnsupportedOperationException if this list does not support the
   *         remove operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
    public fabric.lang.Object remove(int index);
    
    /**
   * Replace an element of this list with another object (optional operation).
   * This implementation uses listIterator(index) and ListIterator.set(o).
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
   * @throws NullPointerException if o is null and the list does not allow
   *         a value to be set to null.
   */
    public fabric.lang.Object set(int index, fabric.lang.Object o);
    
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.AbstractSequentialList {
        public fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$() {
            return ((fabric.util.AbstractSequentialList) fetch()).
              fabric$util$AbstractSequentialList$();
        }
        
        public _Proxy(AbstractSequentialList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.AbstractSequentialList {
        /**
   * The main constructor, for use by subclasses.
   */
        public fabric.util.AbstractSequentialList
          fabric$util$AbstractSequentialList$() {
            fabric$util$AbstractList$();
            return (fabric.util.AbstractSequentialList) this.$getProxy();
        }
        
        /**
   * Returns a ListIterator over the list, starting from position index.
   * Subclasses must provide an implementation of this method.
   *
   * @param index the starting position of the list
   * @return the list iterator
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   */
        public abstract fabric.util.ListIterator listIterator(
          fabric.worker.Store store, int index);
        
        /**
   * Insert an element into the list at a given position (optional operation).
   * This shifts all existing elements from that position to the end one
   * index to the right. This version of add has no return, since it is
   * assumed to always succeed if there is no exception. This iteration
   * uses listIterator(index).add(o).
   *
   * @param index the location to insert the item
   * @param o the object to insert
   * @throws UnsupportedOperationException if this list does not support the
   *         add operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt; size()
   * @throws ClassCastException if o cannot be added to this list due to its
   *         type
   * @throws IllegalArgumentException if o cannot be added to this list for
   *         some other reason.
   * @throws NullPointerException if o is null and the list does not permit
   *         the addition of null values.
   */
        public void add(int index, fabric.lang.Object o) {
            listIterator(
              fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                  get$LOCAL_STORE(),
              index).add(o);
        }
        
        /**
   * Insert the contents of a collection into the list at a given position
   * (optional operation). Shift all elements at that position to the right
   * by the number of elements inserted. This operation is undefined if
   * this list is modified during the operation (for example, if you try
   * to insert a list into itself).
   * <p>
   *
   * This implementation grabs listIterator(index), then proceeds to use add
   * for each element returned by c's iterator. Sun's online specs are wrong,
   * claiming that this also calls next(): listIterator.add() correctly
   * skips the added element.
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
   * @throws NullPointerException if an object, o, in c is null and the list
   *         does not permit the addition of null values.
   * @see #add(int, Object)
   */
        public boolean addAll(int index, fabric.util.Collection c) {
            fabric.util.Iterator
              ci =
              c.
              iterator(
                fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                    get$LOCAL_STORE());
            int size = c.size();
            fabric.util.ListIterator
              i =
              listIterator(
                fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                index);
            for (int pos = size; pos > 0; pos--) i.add(ci.next());
            return size > 0;
        }
        
        /**
   * Get the element at a given index in this list. This implementation
   * returns listIterator(index).next().
   *
   * @param index the index of the element to be returned
   * @return the element at index index in this list
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object get(int index) {
            if (index == size())
                throw new java.lang.IndexOutOfBoundsException("Index: " +
                                                                index +
                                                                ", Size:" +
                                                                size());
            return listIterator(
                     fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                         get$LOCAL_STORE(),
                     index).next();
        }
        
        /**
   * Obtain an Iterator over this list, whose sequence is the list order. This
   * implementation returns listIterator(store).
   *
   * @return an Iterator over the elements of this list, in order
   */
        public fabric.util.Iterator iterator(fabric.worker.Store store) {
            return listIterator(store);
        }
        
        /**
   * Remove the element at a given position in this list (optional operation).
   * Shifts all remaining elements to the left to fill the gap. This
   * implementation uses listIterator(index) and ListIterator.remove().
   *
   * @param index the position within the list of the object to remove
   * @return the object that was removed
   * @throws UnsupportedOperationException if this list does not support the
   *         remove operation
   * @throws IndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
   */
        public fabric.lang.Object remove(int index) {
            if (index == size())
                throw new java.lang.IndexOutOfBoundsException("Index: " +
                                                                index +
                                                                ", Size:" +
                                                                size());
            fabric.util.ListIterator
              i =
              listIterator(
                fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                index);
            fabric.lang.Object removed = i.next();
            i.remove();
            return removed;
        }
        
        /**
   * Replace an element of this list with another object (optional operation).
   * This implementation uses listIterator(index) and ListIterator.set(o).
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
   * @throws NullPointerException if o is null and the list does not allow
   *         a value to be set to null.
   */
        public fabric.lang.Object set(int index, fabric.lang.Object o) {
            if (index == size())
                throw new java.lang.IndexOutOfBoundsException("Index: " +
                                                                index +
                                                                ", Size:" +
                                                                size());
            fabric.util.ListIterator
              i =
              listIterator(
                fabric.util.AbstractSequentialList._Static._Proxy.$instance.
                    get$LOCAL_STORE(),
                index);
            fabric.lang.Object old = i.next();
            i.set(o);
            return old;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.AbstractSequentialList._Proxy(this);
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
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public fabric.worker.Store get$LOCAL_STORE();
        
        public fabric.worker.Store set$LOCAL_STORE(fabric.worker.Store val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.AbstractSequentialList._Static {
            public fabric.worker.Store get$LOCAL_STORE() {
                return ((fabric.util.AbstractSequentialList._Static._Impl)
                          fetch()).get$LOCAL_STORE();
            }
            
            public fabric.worker.Store set$LOCAL_STORE(
              fabric.worker.Store val) {
                return ((fabric.util.AbstractSequentialList._Static._Impl)
                          fetch()).set$LOCAL_STORE(val);
            }
            
            public _Proxy(fabric.util.AbstractSequentialList._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.AbstractSequentialList._Static
              $instance;
            
            static {
                fabric.
                  util.
                  AbstractSequentialList.
                  _Static.
                  _Impl
                  impl =
                  (fabric.util.AbstractSequentialList._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.util.AbstractSequentialList._Static._Impl.class);
                $instance = (fabric.util.AbstractSequentialList._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.AbstractSequentialList._Static {
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
                return new fabric.util.AbstractSequentialList._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm591 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled594 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff592 = 1;
                        boolean $doBackoff593 = true;
                        boolean $retry587 = true;
                        boolean $keepReads588 = false;
                        $label585: for (boolean $commit586 = false; !$commit586;
                                        ) {
                            if ($backoffEnabled594) {
                                if ($doBackoff593) {
                                    if ($backoff592 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff592));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e589) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff592 < 5000) $backoff592 *= 2;
                                }
                                $doBackoff593 = $backoff592 <= 32 ||
                                                  !$doBackoff593;
                            }
                            $commit586 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.AbstractSequentialList._Static.
                                      _Proxy.
                                      $instance.
                                      set$LOCAL_STORE(
                                        fabric.worker.Worker.getWorker().
                                            getLocalStore());
                                }
                                catch (final fabric.worker.
                                         RetryException $e589) {
                                    throw $e589;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e589) {
                                    throw $e589;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e589) {
                                    throw $e589;
                                }
                                catch (final Throwable $e589) {
                                    $tm591.getCurrentLog().checkRetrySignal();
                                    throw $e589;
                                }
                            }
                            catch (final fabric.worker.RetryException $e589) {
                                $commit586 = false;
                                continue $label585;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e589) {
                                $commit586 = false;
                                $retry587 = false;
                                $keepReads588 = $e589.keepReads;
                                if ($tm591.checkForStaleObjects()) {
                                    $retry587 = true;
                                    $keepReads588 = false;
                                    continue $label585;
                                }
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid ==
                                      null ||
                                      !$e589.tid.isDescendantOf(
                                                   $currentTid590)) {
                                    throw $e589;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e589);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e589) {
                                $commit586 = false;
                                fabric.common.TransactionID $currentTid590 =
                                  $tm591.getCurrentTid();
                                if ($e589.tid.isDescendantOf($currentTid590))
                                    continue $label585;
                                if ($currentTid590.parent != null) {
                                    $retry587 = false;
                                    throw $e589;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e589) {
                                $commit586 = false;
                                if ($tm591.checkForStaleObjects())
                                    continue $label585;
                                $retry587 = false;
                                throw new fabric.worker.AbortException($e589);
                            }
                            finally {
                                if ($commit586) {
                                    fabric.common.TransactionID $currentTid590 =
                                      $tm591.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e589) {
                                        $commit586 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e589) {
                                        $commit586 = false;
                                        $retry587 = false;
                                        $keepReads588 = $e589.keepReads;
                                        if ($tm591.checkForStaleObjects()) {
                                            $retry587 = true;
                                            $keepReads588 = false;
                                            continue $label585;
                                        }
                                        if ($e589.tid ==
                                              null ||
                                              !$e589.tid.isDescendantOf(
                                                           $currentTid590))
                                            throw $e589;
                                        throw new fabric.worker.
                                                UserAbortException($e589);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e589) {
                                        $commit586 = false;
                                        $currentTid590 = $tm591.getCurrentTid();
                                        if ($currentTid590 != null) {
                                            if ($e589.tid.equals(
                                                            $currentTid590) ||
                                                  !$e589.tid.
                                                  isDescendantOf(
                                                    $currentTid590)) {
                                                throw $e589;
                                            }
                                        }
                                    }
                                } else if ($keepReads588) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit586) {
                                    {  }
                                    if ($retry587) { continue $label585; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -89, 39, 102, 35, 78,
    19, -122, -51, 63, -122, 90, 13, -87, -72, -2, -97, 97, 75, 82, 91, 76, -19,
    16, 14, 78, -55, 96, 44, 77, -89, 124, -87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
