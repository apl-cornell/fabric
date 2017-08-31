package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.LinkedHashSet;
import java.util.LinkedList;
import fabric.util.Set;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import java.util.logging.Level;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              set$observers(val);
        }
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getStore();
        }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).
              fabric$metrics$util$AbstractSubject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).removeObserver(
                                                              arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.AbstractSubject) fetch()).observedBy(
                                                                     arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getObservers(
                                                                     );
        }
        
        public static void processSamples(java.util.LinkedList arg1) {
            fabric.metrics.util.AbstractSubject._Impl.processSamples(arg1);
        }
        
        public _Proxy(AbstractSubject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.AbstractSubject {
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            this.set$observers(
                   ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$util$LinkedHashSet$());
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() { return this.observers; }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers;
        
        public void addObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm472 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff473 = 1;
                boolean $doBackoff474 = true;
                $label468: for (boolean $commit469 = false; !$commit469; ) {
                    if ($doBackoff474) {
                        if ($backoff473 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff473);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e470) {
                                    
                                }
                            }
                        }
                        if ($backoff473 < 5000) $backoff473 *= 1;
                    }
                    $doBackoff474 = $backoff473 <= 32 || !$doBackoff474;
                    $commit469 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().add(o); }
                    catch (final fabric.worker.RetryException $e470) {
                        $commit469 = false;
                        continue $label468;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e470) {
                        $commit469 = false;
                        fabric.common.TransactionID $currentTid471 =
                          $tm472.getCurrentTid();
                        if ($e470.tid.isDescendantOf($currentTid471))
                            continue $label468;
                        if ($currentTid471.parent != null) throw $e470;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e470) {
                        $commit469 = false;
                        if ($tm472.checkForStaleObjects()) continue $label468;
                        throw new fabric.worker.AbortException($e470);
                    }
                    finally {
                        if ($commit469) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e470) {
                                $commit469 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e470) {
                                $commit469 = false;
                                fabric.common.TransactionID $currentTid471 =
                                  $tm472.getCurrentTid();
                                if ($currentTid471 != null) {
                                    if ($e470.tid.equals($currentTid471) ||
                                          !$e470.tid.isDescendantOf(
                                                       $currentTid471)) {
                                        throw $e470;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit469) {
                            {  }
                            continue $label468;
                        }
                    }
                }
            }
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm479 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff480 = 1;
                boolean $doBackoff481 = true;
                $label475: for (boolean $commit476 = false; !$commit476; ) {
                    if ($doBackoff481) {
                        if ($backoff480 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff480);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e477) {
                                    
                                }
                            }
                        }
                        if ($backoff480 < 5000) $backoff480 *= 1;
                    }
                    $doBackoff481 = $backoff480 <= 32 || !$doBackoff481;
                    $commit476 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().remove(o); }
                    catch (final fabric.worker.RetryException $e477) {
                        $commit476 = false;
                        continue $label475;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e477) {
                        $commit476 = false;
                        fabric.common.TransactionID $currentTid478 =
                          $tm479.getCurrentTid();
                        if ($e477.tid.isDescendantOf($currentTid478))
                            continue $label475;
                        if ($currentTid478.parent != null) throw $e477;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e477) {
                        $commit476 = false;
                        if ($tm479.checkForStaleObjects()) continue $label475;
                        throw new fabric.worker.AbortException($e477);
                    }
                    finally {
                        if ($commit476) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e477) {
                                $commit476 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e477) {
                                $commit476 = false;
                                fabric.common.TransactionID $currentTid478 =
                                  $tm479.getCurrentTid();
                                if ($currentTid478 != null) {
                                    if ($e477.tid.equals($currentTid478) ||
                                          !$e477.tid.isDescendantOf(
                                                       $currentTid478)) {
                                        throw $e477;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit476) {
                            {  }
                            continue $label475;
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   *
   * @param unobserved
   *            a {@link LinkedList} of {@link SampledMetric}s that have been
   *            updated and still need to have Observers compute the resulting
   *            effects for.
   */
        public static void processSamples(java.util.LinkedList unobserved) {
            java.util.LinkedList queue = new java.util.LinkedList();
            java.util.LinkedList delayed = new java.util.LinkedList();
            while (!unobserved.isEmpty()) {
                fabric.metrics.SampledMetric
                  sm =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(
                                                          unobserved.poll()));
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "HANDLING OBSERVATION OF " +
                      java.lang.String.
                        valueOf(fabric.lang.WrappedJavaInlineable.$unwrap(sm)));
                fabric.util.Iterator obsIter = sm.getObservers().iterator();
                while (obsIter.hasNext()) {
                    queue.add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  obsIter.next(
                                                                            )));
                }
                while (!queue.isEmpty()) {
                    fabric.metrics.util.Observer
                      unhandled =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              queue.poll()));
                    delayed.remove(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                    unhandled));
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
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINE,
                            "HANDLING OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap(unhandled)));
                        boolean modified = unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              modified) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINE,
                                "QUEUING PARENTS OF OBSERVER " +
                                  java.lang.String.
                                    valueOf(
                                      fabric.lang.WrappedJavaInlineable.
                                          $unwrap(unhandled)));
                            fabric.util.Iterator parentIter =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers().iterator();
                            while (parentIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parentIter.next()));
                            }
                        }
                    }
                    else {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINE,
                            "DELAYING OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap(unhandled)));
                        delayed.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  unhandled));
                    }
                    if (queue.isEmpty()) {
                        java.util.Iterator delayedIter = delayed.iterator();
                        while (delayedIter.hasNext()) {
                            fabric.metrics.util.Observer
                              withheld =
                              (fabric.metrics.util.Observer)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(delayedIter.next()));
                            boolean doneWaiting = true;
                            fabric.util.Iterator withheldLeavesIter =
                              withheld.getLeafSubjects().iterator();
                            while (withheldLeavesIter.hasNext()) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(withheldLeavesIter.next()))) {
                                    doneWaiting = false;
                                    break;
                                }
                            }
                            if (doneWaiting) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(withheld));
                            }
                        }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.AbstractSubject._Proxy(this);
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
            fabric.metrics.util.AbstractSubject._Impl src =
              (fabric.metrics.util.AbstractSubject._Impl) other;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.AbstractSubject._Static {
            public _Proxy(fabric.metrics.util.AbstractSubject._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.AbstractSubject._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  AbstractSubject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.AbstractSubject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.AbstractSubject._Static._Impl.class);
                $instance = (fabric.metrics.util.AbstractSubject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.AbstractSubject._Static {
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
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 79, 92, 97, -53, -81,
    59, -82, 126, -37, -61, -103, 66, -63, 76, -116, 34, 25, -81, -123, 106,
    -72, 27, -83, 124, -110, -24, -98, 28, -12, 108, 93, -92 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504188540000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
