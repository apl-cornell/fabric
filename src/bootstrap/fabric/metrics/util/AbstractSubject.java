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
                fabric.worker.transaction.TransactionManager $tm538 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled541 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff539 = 1;
                boolean $doBackoff540 = true;
                $label534: for (boolean $commit535 = false; !$commit535; ) {
                    if ($backoffEnabled541) {
                        if ($doBackoff540) {
                            if ($backoff539 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff539);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e536) {
                                        
                                    }
                                }
                            }
                            if ($backoff539 < 5000) $backoff539 *= 2;
                        }
                        $doBackoff540 = $backoff539 <= 32 || !$doBackoff540;
                    }
                    $commit535 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finer(
                            "ADD OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      o)) +
                              " FOR " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      tmp)) +
                              " IN " +
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentTid());
                        tmp.get$observers().add(o);
                    }
                    catch (final fabric.worker.RetryException $e536) {
                        $commit535 = false;
                        continue $label534;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e536) {
                        $commit535 = false;
                        fabric.common.TransactionID $currentTid537 =
                          $tm538.getCurrentTid();
                        if ($e536.tid.isDescendantOf($currentTid537))
                            continue $label534;
                        if ($currentTid537.parent != null) throw $e536;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e536) {
                        $commit535 = false;
                        if ($tm538.checkForStaleObjects()) continue $label534;
                        throw new fabric.worker.AbortException($e536);
                    }
                    finally {
                        if ($commit535) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e536) {
                                $commit535 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e536) {
                                $commit535 = false;
                                fabric.common.TransactionID $currentTid537 =
                                  $tm538.getCurrentTid();
                                if ($currentTid537 != null) {
                                    if ($e536.tid.equals($currentTid537) ||
                                          !$e536.tid.isDescendantOf(
                                                       $currentTid537)) {
                                        throw $e536;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit535) {
                            {  }
                            continue $label534;
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
                fabric.worker.transaction.TransactionManager $tm546 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled549 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff547 = 1;
                boolean $doBackoff548 = true;
                $label542: for (boolean $commit543 = false; !$commit543; ) {
                    if ($backoffEnabled549) {
                        if ($doBackoff548) {
                            if ($backoff547 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff547);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e544) {
                                        
                                    }
                                }
                            }
                            if ($backoff547 < 5000) $backoff547 *= 2;
                        }
                        $doBackoff548 = $backoff547 <= 32 || !$doBackoff548;
                    }
                    $commit543 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          finer(
                            "REMOVE OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      o)) +
                              " FOR " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      tmp)) +
                              " IN " +
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentTid());
                        tmp.get$observers().remove(o);
                    }
                    catch (final fabric.worker.RetryException $e544) {
                        $commit543 = false;
                        continue $label542;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e544) {
                        $commit543 = false;
                        fabric.common.TransactionID $currentTid545 =
                          $tm546.getCurrentTid();
                        if ($e544.tid.isDescendantOf($currentTid545))
                            continue $label542;
                        if ($currentTid545.parent != null) throw $e544;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e544) {
                        $commit543 = false;
                        if ($tm546.checkForStaleObjects()) continue $label542;
                        throw new fabric.worker.AbortException($e544);
                    }
                    finally {
                        if ($commit543) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e544) {
                                $commit543 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e544) {
                                $commit543 = false;
                                fabric.common.TransactionID $currentTid545 =
                                  $tm546.getCurrentTid();
                                if ($currentTid545 != null) {
                                    if ($e544.tid.equals($currentTid545) ||
                                          !$e544.tid.isDescendantOf(
                                                       $currentTid545)) {
                                        throw $e544;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit543) {
                            {  }
                            continue $label542;
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
    
    public static final byte[] $classHash = new byte[] { 126, 111, 17, 42, -56,
    5, -9, -125, 60, 85, -40, 89, -110, -20, 106, 6, 2, -106, 30, -86, 26, 39,
    96, 76, 52, 9, -7, -127, 41, -61, -89, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508291589000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
