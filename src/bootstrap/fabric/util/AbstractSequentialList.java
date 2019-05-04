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
    
    public static final byte[] $classHash = new byte[] { 66, 9, -89, -117, 116,
    103, 124, 124, -21, -84, -13, 78, -69, 16, -69, -23, -53, -71, 104, 110, 50,
    -88, -97, -95, 102, 43, -86, 33, -94, 62, -99, -119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
