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
                        fabric.worker.transaction.TransactionManager $tm569 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled572 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff570 = 1;
                        boolean $doBackoff571 = true;
                        boolean $retry565 = true;
                        boolean $keepReads566 = false;
                        $label563: for (boolean $commit564 = false; !$commit564;
                                        ) {
                            if ($backoffEnabled572) {
                                if ($doBackoff571) {
                                    if ($backoff570 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff570));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e567) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff570 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff570 =
                                          java.lang.Math.
                                            min(
                                              $backoff570 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff571 = $backoff570 <= 32 ||
                                                  !$doBackoff571;
                            }
                            $commit564 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.AbstractSequentialList._Static.
                                  _Proxy.
                                  $instance.
                                  set$LOCAL_STORE(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                            }
                            catch (final fabric.worker.RetryException $e567) {
                                $commit564 = false;
                                continue $label563;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e567) {
                                $commit564 = false;
                                $retry565 = false;
                                $keepReads566 = $e567.keepReads;
                                fabric.common.TransactionID $currentTid568 =
                                  $tm569.getCurrentTid();
                                if ($e567.tid ==
                                      null ||
                                      !$e567.tid.isDescendantOf(
                                                   $currentTid568)) {
                                    throw $e567;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e567);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e567) {
                                $commit564 = false;
                                fabric.common.TransactionID $currentTid568 =
                                  $tm569.getCurrentTid();
                                if ($e567.tid.isDescendantOf($currentTid568))
                                    continue $label563;
                                if ($currentTid568.parent != null) {
                                    $retry565 = false;
                                    throw $e567;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e567) {
                                $commit564 = false;
                                $retry565 = false;
                                if ($tm569.inNestedTxn()) {
                                    $keepReads566 = true;
                                }
                                throw $e567;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid568 =
                                  $tm569.getCurrentTid();
                                if ($commit564) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e567) {
                                        $commit564 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e567) {
                                        $commit564 = false;
                                        $retry565 = false;
                                        $keepReads566 = $e567.keepReads;
                                        if ($e567.tid ==
                                              null ||
                                              !$e567.tid.isDescendantOf(
                                                           $currentTid568))
                                            throw $e567;
                                        throw new fabric.worker.
                                                UserAbortException($e567);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e567) {
                                        $commit564 = false;
                                        $currentTid568 = $tm569.getCurrentTid();
                                        if ($currentTid568 != null) {
                                            if ($e567.tid.equals(
                                                            $currentTid568) ||
                                                  !$e567.tid.
                                                  isDescendantOf(
                                                    $currentTid568)) {
                                                throw $e567;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm569.inNestedTxn() &&
                                          $tm569.checkForStaleObjects()) {
                                        $retry565 = true;
                                        $keepReads566 = false;
                                    }
                                    if ($keepReads566) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e567) {
                                            $currentTid568 = $tm569.getCurrentTid();
                                            if ($currentTid568 != null &&
                                                  ($e567.tid.equals($currentTid568) || !$e567.tid.isDescendantOf($currentTid568))) {
                                                throw $e567;
                                            } else {
                                                $retry565 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit564) {
                                    {  }
                                    if ($retry565) { continue $label563; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 119, -65, 70, 105, 46,
    39, 53, -26, -40, -102, 48, 88, -117, 59, 14, 39, 6, 81, 102, 116, -55, 15,
    84, 12, -121, 91, -85, -13, 105, -20, 84, 70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
