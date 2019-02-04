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
                        fabric.worker.transaction.TransactionManager $tm540 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled543 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff541 = 1;
                        boolean $doBackoff542 = true;
                        boolean $retry536 = true;
                        boolean $keepReads537 = false;
                        $label534: for (boolean $commit535 = false; !$commit535;
                                        ) {
                            if ($backoffEnabled543) {
                                if ($doBackoff542) {
                                    if ($backoff541 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff541));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e538) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff541 < 5000) $backoff541 *= 2;
                                }
                                $doBackoff542 = $backoff541 <= 32 ||
                                                  !$doBackoff542;
                            }
                            $commit535 = true;
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
                                         RetryException $e538) {
                                    throw $e538;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e538) {
                                    throw $e538;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e538) {
                                    throw $e538;
                                }
                                catch (final Throwable $e538) {
                                    $tm540.getCurrentLog().checkRetrySignal();
                                    throw $e538;
                                }
                            }
                            catch (final fabric.worker.RetryException $e538) {
                                $commit535 = false;
                                continue $label534;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e538) {
                                $commit535 = false;
                                $retry536 = false;
                                $keepReads537 = $e538.keepReads;
                                if ($tm540.checkForStaleObjects()) {
                                    $retry536 = true;
                                    $keepReads537 = false;
                                    continue $label534;
                                }
                                fabric.common.TransactionID $currentTid539 =
                                  $tm540.getCurrentTid();
                                if ($e538.tid ==
                                      null ||
                                      !$e538.tid.isDescendantOf(
                                                   $currentTid539)) {
                                    throw $e538;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e538);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e538) {
                                $commit535 = false;
                                fabric.common.TransactionID $currentTid539 =
                                  $tm540.getCurrentTid();
                                if ($e538.tid.isDescendantOf($currentTid539))
                                    continue $label534;
                                if ($currentTid539.parent != null) {
                                    $retry536 = false;
                                    throw $e538;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e538) {
                                $commit535 = false;
                                if ($tm540.checkForStaleObjects())
                                    continue $label534;
                                $retry536 = false;
                                throw new fabric.worker.AbortException($e538);
                            }
                            finally {
                                if ($commit535) {
                                    fabric.common.TransactionID $currentTid539 =
                                      $tm540.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e538) {
                                        $commit535 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e538) {
                                        $commit535 = false;
                                        $retry536 = false;
                                        $keepReads537 = $e538.keepReads;
                                        if ($tm540.checkForStaleObjects()) {
                                            $retry536 = true;
                                            $keepReads537 = false;
                                            continue $label534;
                                        }
                                        if ($e538.tid ==
                                              null ||
                                              !$e538.tid.isDescendantOf(
                                                           $currentTid539))
                                            throw $e538;
                                        throw new fabric.worker.
                                                UserAbortException($e538);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e538) {
                                        $commit535 = false;
                                        $currentTid539 = $tm540.getCurrentTid();
                                        if ($currentTid539 != null) {
                                            if ($e538.tid.equals(
                                                            $currentTid539) ||
                                                  !$e538.tid.
                                                  isDescendantOf(
                                                    $currentTid539)) {
                                                throw $e538;
                                            }
                                        }
                                    }
                                } else if ($keepReads537) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit535) {
                                    {  }
                                    if ($retry536) { continue $label534; }
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
      "H4sIAAAAAAAAALVYb2wcRxWfO9tnn+P4b+Imru049pE2/+5IgA+NoWCfkvrIJTY+J1JstZe53bnzxnu7m905+5zUqI2giSoRodZNG0EDgkCa4iaA1D9SZSlIQBsFkECowAcgXyqKQoQq1MIHaHlvdu/2vHdn4g9YmnnjmTdv3p/fezN7S3dJnWWS/jRNKWqYzxvMCh+gqVh8jJoWk6MqtawJmE1K62pjF967Ivf6iT9OmiSq6ZoiUTWpWZw0x0/QWRrRGI8cGY8NTpGghBtHqDXNiX9qOG+SPkNX5zOqzp1DyuQ/tzOy+PxjrT+uIS2TpEXREpxyRYrqGmd5PkmasiybYqY1JMtMniRtGmNygpkKVZVTwKhrk6TdUjIa5TmTWePM0tVZZGy3cgYzxZmFSVRfB7XNnMR1E9RvtdXPcUWNxBWLD8ZJIK0wVbZOki+T2jipS6s0A4yd8YIVESExcgDngb1RATXNNJVYYUvtjKLJnGzx7ihaHDoIDLC1Psv4tF48qlajMEHabZVUqmUiCW4qWgZY6/QcnMJJV1WhwNRgUGmGZliSk01evjF7CbiCwi24hZONXjYhCWLW5YlZSbTuHv7s+dPaiOYnPtBZZpKK+jfApl7PpnGWZibTJGZvbNoRv0A7l8/5CQHmjR5mm+f1x9//wq7eG2/bPPdX4BlNnWAST0qXU82/7o5uf6gG1WgwdEtBKKywXER1zFkZzBuA9s6iRFwMFxZvjP/82BMvszt+0hgjAUlXc1lAVZukZw1FZeYjTGMm5UyOkSDT5KhYj5F6GMcVjdmzo+m0xXiM1KpiKqCL/8FFaRCBLqqHsaKl9cLYoHxajPMGIaQVGvFBu0XIlgeBhgipm+fkaGRaz7JISs2xOYB3BBqjpjQdgbw1FWm3pBvzEcuUImZO4wpw2vO28UMpwDqVeIKdzDFYpiqCPAwaGf83yXm0qXXO5wN3b5F0maWoBbFzcDQ8pkKqjOiqzMykpJ5fjpGO5YsCS0HEvwUYFt7yQfy7vZWjdO9ibnj/+9eSt2wc4l7HmVzUNNDUjnFlTUG5JsyzMFSuMFSuJV8+HL0U+4GAU8ASeVeU1wTy9hkq5WndzOaJzyeM2yD2izMABTNQXUBu0/bEo188fq6/BgBszNViTIE15E0ntwjFYEQhR5JSy9n3Prx+YUF3E4uTUFm+l+/EfO33esrUJSZDPXTF7+ijryaXF0J+rDVBKIOcAlChpvR6z1iRt4OFGojeqIuTdegDquJSoXA18mlTn3NnBAKasWu3wYDO8igoyufnEsaLv//VXz8lLpZCpW0pKckJxgdLshuFtYg8bnN9P2EyBnx/fGHs2efunp0SjgeOgUoHhrCPQlZTSGfd/OrbJ//w5z9d/q3fDRYnASOXUhUpL2xp+xj+fNA+woYpihNIoVBHnfLQV6wPBp68zdUNKoUK1QpUt0JHtKwuK2mFplSGSPl3yyf2vPq38612uFWYsZ1nkl3/W4A7v3mYPHHrsX/2CjE+CW8q138um13+OlzJQ6ZJ51GP/JO/6bn4Fn0RkA/Fy1JOMVGPiPAHEQHcK3yxW/R7PGufxq7f9lZ3EfDeq+AA3qkuFicjS9/sij58x877IhZRxtYKeX+UlqTJ3pezH/j7Az/zk/pJ0iquc6rxoxTKGMBgEi5kK+pMxsn6FesrL1f7Jhks5lq3Nw9KjvVmgVtvYIzcOG60gW8DBxyxGZ30GWhQzgO9QB+Akv4WrnYY2G/I+4gY7BNbBkS/DbvtwpE1nAQNU+egJYMHBVQlfBfBpJLN5jjCQBy4k5N18dHoUDyZmBgd3y+kbOSkwymCc7o5w8xwAvBu5+dmb0ETOZqvrIMfhzs4aaBOGc0XzRN/Lc5NlXeoUmJeCSZIHkDRU+1RIR5El88sXpJHv7fHvvrbV17U+7Vc9pV3/vOL8Au3b1Yo+EGuG7tVNsvUkjMb4MitZa/bQ+LN5cLp9p2eh6Iz72bsY7d4VPRyXz20dPORbdIzflJTxE3ZQ2/lpsGVaGk0GbxTtYkVmOkrOrUJnTUCbTdg5vsOPVGKGbuiVgyWD4cPuxHyo7B1jhDFoce9Eaqc18dWWZvCLsHJgA2xEIYoVPmeDbnqjhX1akdJX4I2CFGaduj2ezTSRiR2Ix5L2xxJDzp0c3VL/a6o4QrlasxUsnDjzDovV3Zu8emPw+cXbeTZz/uBshd26R77iS/MWC9yFPG/dbVTxI4Df7m+8OZLC2f9jqPjnNTA94UYS6sEZAa7RyGv8RqJcSZut0Ih2FT6Gop7GcqqgSdWQTxkG7QxAOc+h/asBZA7PGFqcIR0O7TznsKULpjT7piDNTxs1/DKhgjF5lbx2gJ2J8HFVLY/wmQnVEgynNTO6opcyR2fhGYCjI85dG8Vd2DHy43HLXscunNtxneWxrLkcVDdAWdXccDXsDsDNws4YEhVK/mgPqXrKqNaJTfglfYVQjZ83aGn1+YG3HLKoby6G3wuktJC6vOrWHQRu2cgpBkmRFZEM17JzxLSec2hZ9amN2550qGn70nvYSH126vo/R3svgHXrOJJ3g2lAV974m6F9l14iTTbdNOHazMVt3zg0L+vIUSvrGLqdexeAtCZLKvPsqpRwip+g5Cedxz6+tpUxy2vOfSH95ZkQhUh+rVV9H8Dux8BxCwHYnlIy8qXHz6576/wCez8TCNFf8ouv3tw18Yqn7+byn44c/Zdu9TScN+lI78Tn3LFn2CC8KWUzqlq6dO0ZBwwTJZWhAlB+6FqCLIMz8cSkEHZQyJMf9PmuAHBsjnwv58IP3aJruIVU/CF+MyuXpi6cib+QLj0j/v+FWiYuC2+wcDHfVceSA8c7njql59/anL91Tc++hY9OD4Vv9vafPjm8V2Hrjx+9b9pdes/uBQAAA==";
}
