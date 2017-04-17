package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import java.util.logging.Level;

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.
 */
public interface Subject extends fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.Subject fabric$metrics$util$Subject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
    public void addObserver(fabric.metrics.util.Observer o);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    /**
   * @return true iff there are any observers of this subject, currently.
   */
    public boolean isObserved();
    
    /**
   * @return a copy of the set of the current observers of this subject.
   */
    public fabric.util.Set getObserversCopy();
    
    /**
   * @return the set of the current observers of this subject.
   */
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Subject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.Subject._Impl) fetch()).get$observers(
                                                                   );
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.Subject._Impl) fetch()).set$observers(
                                                                   val);
        }
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.Subject) fetch()).getStore();
        }
        
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            return ((fabric.metrics.util.Subject) fetch()).
              fabric$metrics$util$Subject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.Subject) fetch()).observedBy(arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.Subject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObserversCopy() {
            return ((fabric.metrics.util.Subject) fetch()).getObserversCopy();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public static void processSamples(java.util.LinkedList arg1,
                                          java.util.List arg2) {
            fabric.metrics.util.Subject._Impl.processSamples(arg1, arg2);
        }
        
        public _Proxy(Subject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.Subject {
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            fabric$lang$Object$();
            return (fabric.metrics.util.Subject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observers;
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers =
          ((fabric.util.HashSet)
             new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
          fabric$util$HashSet$();
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            this.get$observers().add(o);
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            this.get$observers().remove(o);
        }
        
        /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        /**
   * @return true iff there are any observers of this subject, currently.
   */
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        /**
   * @return a copy of the set of the current observers of this subject.
   */
        public fabric.util.Set getObserversCopy() {
            return ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(this.$getStore()).
                      $getProxy()).fabric$util$LinkedHashSet$(
                                     this.get$observers());
        }
        
        /**
   * @return the set of the current observers of this subject.
   */
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved,
                                          java.util.List extensions) {
            {
                fabric.worker.transaction.TransactionManager $tm25 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff26 = 1;
                boolean $doBackoff27 = true;
                $label21: for (boolean $commit22 = false; !$commit22; ) {
                    if ($doBackoff27) {
                        if ($backoff26 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff26);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e23) {  }
                            }
                        }
                        if ($backoff26 < 5000) $backoff26 *= 2;
                    }
                    $doBackoff27 = $backoff26 <= 32 || !$doBackoff27;
                    $commit22 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.util.LinkedList queue = new java.util.LinkedList();
                        while (!unobserved.isEmpty()) {
                            fabric.metrics.SampledMetric
                              sm =
                              (fabric.metrics.SampledMetric)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(unobserved.poll()));
                            fabric.util.Iterator obsIter =
                              sm.getObservers().iterator();
                            while (obsIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(obsIter.next()));
                            }
                            while (!queue.isEmpty()) {
                                fabric.metrics.util.Observer
                                  unhandled =
                                  (fabric.metrics.util.Observer)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      fabric.lang.WrappedJavaInlineable.
                                          $wrap(queue.poll()));
                                boolean needToWait = false;
                                fabric.util.Iterator leavesIter =
                                  unhandled.getLeafSubjects().iterator();
                                while (leavesIter.hasNext()) {
                                    if (unobserved.
                                          contains(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(leavesIter.next()))) {
                                        needToWait = true;
                                        break;
                                    }
                                }
                                if (!needToWait) {
                                    if (fabric.lang.Object._Proxy.
                                          $getProxy(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                unhandled)) instanceof fabric.metrics.contracts.Contract) {
                                        extensions.
                                          remove(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(unhandled));
                                    }
                                    boolean modified =
                                      unhandled.handleUpdates();
                                    if (fabric.lang.Object._Proxy.
                                          $getProxy(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                unhandled)) instanceof fabric.metrics.util.Subject &&
                                          modified) {
                                        fabric.util.Iterator
                                          parentIter =
                                          ((fabric.metrics.util.Subject)
                                             fabric.lang.Object._Proxy.
                                             $getProxy(unhandled)).getObservers(
                                                                     ).iterator(
                                                                         );
                                        while (parentIter.hasNext()) {
                                            queue.
                                              add(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(parentIter.next()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e23) {
                        $commit22 = false;
                        continue $label21;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e23) {
                        $commit22 = false;
                        fabric.common.TransactionID $currentTid24 =
                          $tm25.getCurrentTid();
                        if ($e23.tid.isDescendantOf($currentTid24))
                            continue $label21;
                        if ($currentTid24.parent != null) throw $e23;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e23) {
                        $commit22 = false;
                        if ($tm25.checkForStaleObjects()) continue $label21;
                        throw new fabric.worker.AbortException($e23);
                    }
                    finally {
                        if ($commit22) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e23) {
                                $commit22 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e23) {
                                $commit22 = false;
                                fabric.common.TransactionID $currentTid24 =
                                  $tm25.getCurrentTid();
                                if ($currentTid24 != null) {
                                    if ($e23.tid.equals($currentTid24) ||
                                          !$e23.tid.isDescendantOf(
                                                      $currentTid24)) {
                                        throw $e23;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit22) {  }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.Subject._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.observers, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.observers = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.Subject._Impl src =
              (fabric.metrics.util.Subject._Impl) other;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.Subject._Static {
            public _Proxy(fabric.metrics.util.Subject._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.Subject._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  Subject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.Subject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.Subject._Static._Impl.class);
                $instance = (fabric.metrics.util.Subject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.Subject._Static {
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
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.Subject._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 89, 79, 112, 75, -50,
    40, -59, -81, -18, -105, 7, -128, -127, -32, 55, 2, -104, -125, -81, -66,
    41, -26, 123, 89, 99, 89, -40, -108, 108, -55, -115, -85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492454887000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
