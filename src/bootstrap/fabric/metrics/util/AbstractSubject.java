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
                fabric.worker.transaction.TransactionManager $tm519 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled522 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff520 = 1;
                boolean $doBackoff521 = true;
                $label515: for (boolean $commit516 = false; !$commit516; ) {
                    if ($backoffEnabled522) {
                        if ($doBackoff521) {
                            if ($backoff520 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff520);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e517) {
                                        
                                    }
                                }
                            }
                            if ($backoff520 < 5000) $backoff520 *= 2;
                        }
                        $doBackoff521 = $backoff520 <= 32 || !$doBackoff521;
                    }
                    $commit516 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().add(o); }
                    catch (final fabric.worker.RetryException $e517) {
                        $commit516 = false;
                        continue $label515;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e517) {
                        $commit516 = false;
                        fabric.common.TransactionID $currentTid518 =
                          $tm519.getCurrentTid();
                        if ($e517.tid.isDescendantOf($currentTid518))
                            continue $label515;
                        if ($currentTid518.parent != null) throw $e517;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e517) {
                        $commit516 = false;
                        if ($tm519.checkForStaleObjects()) continue $label515;
                        throw new fabric.worker.AbortException($e517);
                    }
                    finally {
                        if ($commit516) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e517) {
                                $commit516 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e517) {
                                $commit516 = false;
                                fabric.common.TransactionID $currentTid518 =
                                  $tm519.getCurrentTid();
                                if ($currentTid518 != null) {
                                    if ($e517.tid.equals($currentTid518) ||
                                          !$e517.tid.isDescendantOf(
                                                       $currentTid518)) {
                                        throw $e517;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit516) {
                            {  }
                            continue $label515;
                        }
                    }
                }
            }
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm527 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled530 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff528 = 1;
                boolean $doBackoff529 = true;
                $label523: for (boolean $commit524 = false; !$commit524; ) {
                    if ($backoffEnabled530) {
                        if ($doBackoff529) {
                            if ($backoff528 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff528);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e525) {
                                        
                                    }
                                }
                            }
                            if ($backoff528 < 5000) $backoff528 *= 2;
                        }
                        $doBackoff529 = $backoff528 <= 32 || !$doBackoff529;
                    }
                    $commit524 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().remove(o); }
                    catch (final fabric.worker.RetryException $e525) {
                        $commit524 = false;
                        continue $label523;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e525) {
                        $commit524 = false;
                        fabric.common.TransactionID $currentTid526 =
                          $tm527.getCurrentTid();
                        if ($e525.tid.isDescendantOf($currentTid526))
                            continue $label523;
                        if ($currentTid526.parent != null) throw $e525;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e525) {
                        $commit524 = false;
                        if ($tm527.checkForStaleObjects()) continue $label523;
                        throw new fabric.worker.AbortException($e525);
                    }
                    finally {
                        if ($commit524) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e525) {
                                $commit524 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e525) {
                                $commit524 = false;
                                fabric.common.TransactionID $currentTid526 =
                                  $tm527.getCurrentTid();
                                if ($currentTid526 != null) {
                                    if ($e525.tid.equals($currentTid526) ||
                                          !$e525.tid.isDescendantOf(
                                                       $currentTid526)) {
                                        throw $e525;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit524) {
                            {  }
                            continue $label523;
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
                    java.util.logging.Level.FINER,
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
                            java.util.logging.Level.FINER,
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
                                java.util.logging.Level.FINER,
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
                            java.util.logging.Level.FINER,
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
    
    public static final byte[] $classHash = new byte[] { -60, -53, -1, 73, -55,
    99, -44, -61, -64, -14, -61, -46, 88, 49, 87, 69, 120, 33, -47, 31, 30, 72,
    -41, 94, 124, 25, -6, 26, 38, 62, 113, 3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
