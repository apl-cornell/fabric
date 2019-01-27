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
                        fabric.worker.transaction.TransactionManager $tm581 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled584 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff582 = 1;
                        boolean $doBackoff583 = true;
                        boolean $retry577 = true;
                        boolean $keepReads578 = false;
                        $label575: for (boolean $commit576 = false; !$commit576;
                                        ) {
                            if ($backoffEnabled584) {
                                if ($doBackoff583) {
                                    if ($backoff582 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff582));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e579) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff582 < 5000) $backoff582 *= 2;
                                }
                                $doBackoff583 = $backoff582 <= 32 ||
                                                  !$doBackoff583;
                            }
                            $commit576 = true;
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
                                         RetryException $e579) {
                                    throw $e579;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e579) {
                                    throw $e579;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e579) {
                                    throw $e579;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e579) {
                                    throw $e579;
                                }
                                catch (final Throwable $e579) {
                                    $tm581.getCurrentLog().checkRetrySignal();
                                    throw $e579;
                                }
                            }
                            catch (final fabric.worker.RetryException $e579) {
                                $commit576 = false;
                                continue $label575;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e579) {
                                $commit576 = false;
                                $retry577 = false;
                                $keepReads578 = $e579.keepReads;
                                if ($tm581.checkForStaleObjects()) {
                                    $retry577 = true;
                                    $keepReads578 = false;
                                    continue $label575;
                                }
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid ==
                                      null ||
                                      !$e579.tid.isDescendantOf(
                                                   $currentTid580)) {
                                    throw $e579;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e579);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e579) {
                                $commit576 = false;
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid.isDescendantOf($currentTid580))
                                    continue $label575;
                                if ($currentTid580.parent != null) {
                                    $retry577 = false;
                                    throw $e579;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e579) {
                                $commit576 = false;
                                if ($tm581.checkForStaleObjects())
                                    continue $label575;
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid.isDescendantOf($currentTid580)) {
                                    $retry577 = true;
                                }
                                else if ($currentTid580.parent != null) {
                                    $retry577 = false;
                                    throw $e579;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e579) {
                                $commit576 = false;
                                if ($tm581.checkForStaleObjects())
                                    continue $label575;
                                $retry577 = false;
                                throw new fabric.worker.AbortException($e579);
                            }
                            finally {
                                if ($commit576) {
                                    fabric.common.TransactionID $currentTid580 =
                                      $tm581.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e579) {
                                        $commit576 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e579) {
                                        $commit576 = false;
                                        $retry577 = false;
                                        $keepReads578 = $e579.keepReads;
                                        if ($tm581.checkForStaleObjects()) {
                                            $retry577 = true;
                                            $keepReads578 = false;
                                            continue $label575;
                                        }
                                        if ($e579.tid ==
                                              null ||
                                              !$e579.tid.isDescendantOf(
                                                           $currentTid580))
                                            throw $e579;
                                        throw new fabric.worker.
                                                UserAbortException($e579);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e579) {
                                        $commit576 = false;
                                        $currentTid580 = $tm581.getCurrentTid();
                                        if ($currentTid580 != null) {
                                            if ($e579.tid.equals(
                                                            $currentTid580) ||
                                                  !$e579.tid.
                                                  isDescendantOf(
                                                    $currentTid580)) {
                                                throw $e579;
                                            }
                                        }
                                    }
                                } else if ($keepReads578) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit576) {
                                    {  }
                                    if ($retry577) { continue $label575; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 54, -121, -70, 125,
    -98, 71, -38, -36, -12, -73, 83, -30, 68, -115, -128, 82, -96, -71, -53,
    -67, 62, 81, -25, 59, -67, -47, -3, 38, 62, 51, -4, 68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
