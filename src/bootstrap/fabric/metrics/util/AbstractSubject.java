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
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject arg1,
          fabric.metrics.util.Observer arg2) {
            fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                        arg1, arg2);
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
            fabric.metrics.util.AbstractSubject._Impl.
              static_addObserver((fabric.metrics.util.AbstractSubject)
                                   this.$getProxy(), o);
        }
        
        private static void static_addObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
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
                    try { tmp.get$observers().add(o); }
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
            fabric.metrics.util.AbstractSubject._Impl.
              static_removeObserver((fabric.metrics.util.AbstractSubject)
                                      this.$getProxy(), o);
        }
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
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
                    try { tmp.get$observers().remove(o); }
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
    
    public static final byte[] $classHash = new byte[] { 63, -103, 119, 101, 54,
    26, 44, 38, -44, 6, -64, 121, -119, -100, -87, 47, -9, 116, -87, 76, 105,
    66, -68, -99, -115, -5, 75, -33, 120, -19, -126, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234270000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
